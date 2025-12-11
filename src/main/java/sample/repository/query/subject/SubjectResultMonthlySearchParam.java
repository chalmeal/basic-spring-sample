package sample.repository.query.subject;

import lombok.Builder;
import lombok.Getter;

/** 科目別月次成績集計検索クエリパラメータ */
@Getter
@Builder
public class SubjectResultMonthlySearchParam {
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
