package sample.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import sample.utils.exception.CsvExportException;

/** CSVユーティリティ */
@Component
public class CsvUtils {
    /** ヘッダー行 */
    private List<String> headers = new ArrayList<>();
    private List<List<String>> rows = new ArrayList<>();

    /**
     * ヘッダー行作成
     * 
     * @param columns カラム名配列
     * @return ヘッダー行リスト
     */
    public CsvUtils header(String... columns) {
        for (String column : columns) {
            headers.add(column);
        }

        return this;
    }

    /**
     * データ行作成
     * 
     * @param data データリスト
     * @return データ行リスト
     */
    public <T> CsvUtils rows(List<T> data) {
        List<String> row = new ArrayList<>();
        for (T item : data) {
            row.add(item.toString());
        }
        rows.add(row);

        return this;
    }

    /**
     * CSVエクスポート
     * 
     * @return CSVデータ(byte配列)
     */
    public byte[] export() {
        ByteArrayOutputStream csv = new ByteArrayOutputStream();
        try {
            // ヘッダー行書き込み
            csv.write(String.join(",", headers).getBytes());
            csv.write("\n".getBytes());

            // データ行書き込み
            for (List<String> row : rows) {
                csv.write(String.join(",", row).getBytes());
                csv.write("\n".getBytes());
            }
        } catch (IOException e) {
            throw new CsvExportException("CSV出力に失敗しました。", e);
        }

        return csv.toByteArray();
    }

    /**
     * CSVエクスポートレスポンス作成
     * 
     * @param csvData  CSVデータ(byte配列)
     * @param filename ファイル名
     * @return CSVエクスポートレスポンス
     */
    public static ResponseEntity<byte[]> csvExportResponse(byte[] csvData, String filename) {
        // ファイル名をURLエンコード
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8)
                .replace("+", "%20");

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + encodedFilename + "\"")
                .header("Content-Type", "text/csv; charset=UTF-8")
                .body(csvData);
    }

}
