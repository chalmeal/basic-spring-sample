package sample.utils.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.web.multipart.MultipartFile;

import sample.utils.exception.CsvImportException;
import sample.utils.exception.NotFoundException;

/** CSV取込ユーティリティ */
public class CsvImportUtils<T> {
    private MultipartFile csvFile;

    /**
     * CSVファイル設定
     * 
     * @param csvFile CSVファイル
     * @return CsvImportUtilsインスタンス
     * @throws CsvImportException CSV取込例外
     */
    public CsvImportUtils<T> from(MultipartFile csvFile) throws CsvImportException {
        this.csvFile = csvFile;
        if (this.csvFile.isEmpty()) {
            throw new CsvImportException("CSVファイルが空です。");
        }

        return this;
    }

    /**
     * CSVヘッダー検証
     * 
     * @param expectedHeaders 期待されるヘッダー項目
     * @return CsvImportUtilsインスタンス
     * @throws CsvImportException CSV取込例外
     */
    public CsvImportUtils<T> validHeader(String[] expectedHeaders) throws CsvImportException {
        try (InputStream is = this.csvFile.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String headerLine = reader.readLine();
            String[] actualHeaders = headerLine.split(",");
            if (actualHeaders.length != expectedHeaders.length) {
                throw new CsvImportException("CSVヘッダーの項目数が一致しません。");
            }

            for (int i = 0; i < expectedHeaders.length; i++) {
                if (!actualHeaders[i].trim().equals(expectedHeaders[i].trim())) {
                    throw new CsvImportException(
                            String.format("CSVヘッダーの項目が一致しません。期待値: %s, 実際: %s", expectedHeaders[i], actualHeaders[i]));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("CSVヘッダーの検証に失敗しました。", e);
        }

        return this;
    }

    /**
     * CSVパース
     * 
     * @param csvFile CSVファイル
     * @return カラムリスト
     */
    public List<T> parse(Function<String[], T> mapper) {
        List<T> result = new ArrayList<>();
        try (InputStream is = this.csvFile.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                // ヘッダー行をスキップ
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] columns = line.split(",");
                T item = mapper.apply(columns);
                result.add(item);
            }
        } catch (IOException e) {
            throw new RuntimeException("CSVのパースに失敗しました。", e);
        }

        return result;
    }

    /**
     * リソースの存在チェック
     * 
     * @param resource リソース
     * @param column   カラム名
     * @return 存在する場合はtrue
     * @throws NotFoundException リソースが存在しない場合の例外
     */
    public static <T> boolean isEmptyResource(Optional<T> resource, String key, String column)
            throws NotFoundException {
        resource.orElseThrow(
                () -> new NotFoundException(String.format("%sが見つかりませんでした。", column), key));

        return true;
    }

    /**
     * 値が指定範囲内かチェック
     * 
     * @param value  値
     * @param from   範囲開始
     * @param to     範囲終了
     * @param column カラム名
     * @return 範囲内の場合はtrue
     * @throws CsvImportException 範囲外の場合の例外
     */
    public static boolean isBetween(String value, int from, int to, String column) throws CsvImportException {
        int intValue;
        try {
            intValue = Integer.parseInt(value);
            if (intValue < from || intValue > to) {
                throw new CsvImportException(String.format("%sは%dから%dの範囲で指定してください。", column, from, to));
            }
        } catch (NumberFormatException e) {
            throw new CsvImportException(String.format("%sに数値以外が含まれています。", column));
        }

        return true;
    }

}
