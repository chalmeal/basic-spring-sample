package sample.dto.response.subject;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import sample.entity.SubjectResultUserMonthlySearch;

/** ユーザー別月次成績集計検索レスポンス */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectResultUserMonthlySearchResponse {
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

    public SubjectResultUserMonthlySearchResponse(SubjectResultUserMonthlySearch subjectResultUserMonthly) {
        this.year = subjectResultUserMonthly.getYear();
        this.month = subjectResultUserMonthly.getMonth();
        this.userName = subjectResultUserMonthly.getUserName();
        this.totalScore = subjectResultUserMonthly.getTotalScore();
        this.averageScore = subjectResultUserMonthly.getAverageScore();
    }
}
