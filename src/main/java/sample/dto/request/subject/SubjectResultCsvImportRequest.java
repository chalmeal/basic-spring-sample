package sample.dto.request.subject;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import sample.utils.constrains.NotEmptyFile;

/** 科目結果CSV取込リクエスト */
@Data
public class SubjectResultCsvImportRequest {
    /** CSVファイル */
    @NotEmptyFile(message = "CSVファイルが空、または選択されていません。")
    private MultipartFile file;
}
