package sample.dto.request.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/** ログインパスコードリクエスト */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginPasscodeRequest {
    /** メールアドレス */
    @NotNull(message = "メールアドレスは必須です。")
    @Email(message = "メールアドレスの形式が不正です。")
    private String email;

    /** パスコード */
    @NotNull(message = "パスコードは必須です。")
    @Size(min = 6, max = 6, message = "パスコードは6桁で入力してください。")
    private String passcode;
}
