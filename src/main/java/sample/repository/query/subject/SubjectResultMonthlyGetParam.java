package sample.repository.query.subject;

import lombok.Builder;
import lombok.Getter;

/** 科目別月次成績集計取得クエリパラメータ */
@Getter
@Builder
public class SubjectResultMonthlyGetParam {
    /** 年度 */
    private int year;

    /** 月 */
    private int month;
}
