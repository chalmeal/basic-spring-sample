package sample.dto.request.subject;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

/** 科目結果検索リクエスト */
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectResultSearchRequest {
    /** ユーザーID */
    private String userId;

    /** 科目ID */
    private String subjectId;

    /** 得点下限 */
    private String scoreFrom;

    /** 得点上限 */
    private String scoreTo;

    /** ページサイズ */
    private Integer pageSize;

    /** ページ番号 */
    private Integer pageNumber;
}
