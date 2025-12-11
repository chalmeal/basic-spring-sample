package sample.dto.request.subject;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

/** ユーザー別科目月次成績集計検索レスポンス */
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectResultUserMonthlySearchRequest {
    /** ユーザーID */
    private String userId;

    /** 年度 */
    private Integer year;

    /** 月 */
    private Integer month;

    /** ページサイズ */
    private Integer pageSize;

    /** ページ番号 */
    private Integer pageNumber;
}
