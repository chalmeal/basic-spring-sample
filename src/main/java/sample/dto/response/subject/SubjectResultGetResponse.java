package sample.dto.response.subject;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import sample.entity.SubjectResult;

/** 科目成績取得レスポンス */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectResultGetResponse {
    /** ID */
    private Long id;

    /** ユーザーID */
    private String userId;

    /** 科目ID */
    private Long subjectId;

    /** 得点 */
    private Integer score;

    /** 登録日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;

    /** 削除日時 */
    private LocalDateTime deletedAt;

    public SubjectResultGetResponse(SubjectResult subjectResult) {
        this.id = subjectResult.getId();
        this.userId = subjectResult.getUserId();
        this.subjectId = subjectResult.getSubjectId();
        this.score = subjectResult.getScore();
        this.createdAt = subjectResult.getCreatedAt();
        this.updatedAt = subjectResult.getUpdatedAt();
        this.deletedAt = subjectResult.getDeletedAt();
    }
}
