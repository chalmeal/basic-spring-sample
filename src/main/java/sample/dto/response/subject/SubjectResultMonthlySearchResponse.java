package sample.dto.response.subject;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import sample.entity.SubjectResultMonthlySearch;

/** 科目別月次成績集計検索レスポンス */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectResultMonthlySearchResponse {
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
    private Double averageScore;

    public SubjectResultMonthlySearchResponse(SubjectResultMonthlySearch subjectResultMonthly) {
        this.year = subjectResultMonthly.getYear();
        this.month = subjectResultMonthly.getMonth();
        this.subjectName = subjectResultMonthly.getSubjectName();
        this.maxScore = subjectResultMonthly.getMaxScore();
        this.minScore = subjectResultMonthly.getMinScore();
        this.averageScore = subjectResultMonthly.getAvgScore();
    }
}
