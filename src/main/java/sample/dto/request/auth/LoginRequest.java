package sample.dto.request.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/** ログインリクエスト */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginRequest {
    /** メールアドレス */
    @NotNull(message = "メールアドレスは必須です。")
    @Email(message = "メールアドレスの形式が不正です。")
    private String email;

    /** パスワード */
    @NotNull(message = "パスワードは必須です。")
    private String password;
}
