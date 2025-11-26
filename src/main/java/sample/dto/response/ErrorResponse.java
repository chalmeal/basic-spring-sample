package sample.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

/** エラーレスポンス */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * JSON形式の文字列として返す
     * 
     * @return JSON形式の文字列
     */
    public String valueAsString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            // JSONの変換に失敗した場合は固定のエラーメッセージを返す
            return """
                        {
                            "message": "エラーが発生しました。"
                        }
                    """;
        }
    }
}
