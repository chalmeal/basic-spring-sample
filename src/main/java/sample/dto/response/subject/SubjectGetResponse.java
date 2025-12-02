package sample.dto.response.subject;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import sample.entity.Subject;

/** 科目取得レスポンス */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectGetResponse {
    /** ID */
    private Long id;

    /** 科目名 */
    private String name;

    /** 登録日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;

    /** 削除日時 */
    private LocalDateTime deletedAt;

    public SubjectGetResponse(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.createdAt = subject.getCreatedAt();
        this.updatedAt = subject.getUpdatedAt();
        this.deletedAt = subject.getDeletedAt();
    }
}
