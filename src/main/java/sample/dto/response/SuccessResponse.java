package sample.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

/** 成功レスポンス */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SuccessResponse {
    private String message;
    private String target;

    public SuccessResponse(String message, String target) {
        this.message = message;
        this.target = target;
    }
}
