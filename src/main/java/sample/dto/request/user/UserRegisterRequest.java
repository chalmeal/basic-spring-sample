package sample.dto.request.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/** ユーザー登録リクエスト */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRegisterRequest {
    /** ユーザーID */
    @NotNull(message = "ユーザーIDは必須です。")
    @Pattern(regexp = "^[A-Za-z0-9_.-]+$", message = "ユーザーIDは英数字、アンダースコア(_)、ハイフン(-)、ピリオド(.)のみ使用できます")
    @Size(min = 2, max = 50, message = "ユーザーIDは2〜50文字で入力してください。")
    private String userId;

    /** ユーザー名 */
    @NotNull(message = "ユーザー名は必須です。")
    @Size(min = 2, max = 100, message = "ユーザー名は100文字で入力してください。")
    private String username;

    /** パスワード */
    @NotNull(message = "パスワードは必須です。")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "パスワードは英大文字・英小文字・数字をそれぞれ1文字以上含めてください。")
    @Size(min = 8, max = 32, message = "パスワードは8〜32文字で入力してください。")
    private String password;
}
