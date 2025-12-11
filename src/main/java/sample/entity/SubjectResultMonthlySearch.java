package sample.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Data;

/** 科目別月次成績集計エンティティ */
@Data
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public class SubjectResultMonthlySearch {
    /** 年度 */
    private int year;

    /** 月 */
    private int month;

    /** 科目名 */
    private String subjectName;

    /** 最高得点 */
    private Integer maxScore;

    /** 最低得点 */
    private Integer minScore;

    /** 平均得点 */
    private Double avgScore;
}
