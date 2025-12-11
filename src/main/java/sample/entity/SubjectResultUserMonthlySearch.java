package sample.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Data;

/** 科目別ユーザー月次成績集計エンティティ */
@Data
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public class SubjectResultUserMonthlySearch {
    /** 年度 */
    private int year;

    /** 月 */
    private int month;

    /** ユーザー名 */
    private String userName;

    /** 合計点数 */
    private int totalScore;

    /** 平均点数 */
    private double averageScore;
}
