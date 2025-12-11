package sample.dto.request.subject;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

/** 科目別月次成績集計検索リクエスト */
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectResultMonthlySearchRequest {
    /** 科目ID */
    private Long subjectId;

    /** 年度 */
    private Integer year;

    /** 月 */
    private Integer month;

    /** ページサイズ */
    private Integer pageSize;

    /** ページ番号 */
    private Integer pageNumber;
}
