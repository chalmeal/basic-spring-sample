package sample.dto.response.subject;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import sample.entity.SubjectResultSearch;

/** 科目結果検索レスポンス */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectResultSearchResponse {
    /** ID */
    private Long id;

    /** ユーザー名（管理者のみ） */
    private String userName;

    /** 科目名 */
    private String subjectName;

    /** 得点 */
    private Integer score;

    public SubjectResultSearchResponse(SubjectResultSearch subjectResult) {
        this.id = subjectResult.getId();
        this.userName = subjectResult.getUserName();
        this.subjectName = subjectResult.getSubjectName();
        this.score = subjectResult.getScore();
    }

}
