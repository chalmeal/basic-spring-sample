package sample.repository.query.subject;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/** 科目結果CSVインポートクエリパラメータ */
@Data
public class SubjectResultCsvImportParam {
    /** ユーザーID */
    @NotNull
    private String userId;

    /** 科目ID */
    @NotNull
    private String subjectId;

    /** 得点 */
    @NotNull
    private String score;
}
