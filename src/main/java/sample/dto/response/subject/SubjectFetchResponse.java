package sample.dto.response.subject;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import sample.entity.Subject;

/** 科目一覧取得レスポンス */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectFetchResponse {
    /** ID */
    private Long id;

    /** 科目名 */
    private String name;

    public SubjectFetchResponse(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
    }
}
