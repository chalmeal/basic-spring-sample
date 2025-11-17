package sample.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/** ユーザー仮登録リクエスト */
@Getter
public class UserRegisterTemporaryRequest {
    /** メールアドレス */
    @NotNull(message = "メールアドレスは必須です。")
    @Size(max = 200, message = "メールアドレスは200文字以内で入力してください。")
    @Email(message = "メールアドレスの形式が不正です。")
    private String email;
}
