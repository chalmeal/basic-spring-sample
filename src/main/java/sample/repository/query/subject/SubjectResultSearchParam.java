package sample.repository.query.subject;

import lombok.Builder;
import lombok.Getter;

/** 科目結果検索クエリパラメータ */
@Getter
@Builder
public class SubjectResultSearchParam {
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
