package sample.repository.query.subject;

import lombok.Builder;
import lombok.Getter;

/** ユーザー別月次成績集計検索クエリパラメータ */
@Getter
@Builder
public class SubjectResultUserMonthlySearchParam {
    /** ユーザーID */
    private String userId;

    /** 年度 */
    private Integer year;

    /** 月 */
    private Integer month;
}
