package sample.dto.request.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/** ログイン後パスワード変更リクエスト */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PasswordChangeLoginAfterRequest {
    /** 現在のパスワード */
    @NotNull(message = "現在のパスワードは必須です。")
    private String currentPassword;

    /** 新しいパスワード */
    @NotNull(message = "新しいパスワードは必須です。")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "パスワードは英大文字・英小文字・数字をそれぞれ1文字以上含めてください。")
    @Size(min = 8, max = 32, message = "パスワードは8〜32文字で入力してください。")
    private String newPassword;

    /** 新しいパスワード（確認用） */
    @NotNull(message = "新しいパスワード（確認用）は必須です。")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "パスワードは英大文字・英小文字・数字をそれぞれ1文字以上含めてください。")
    @Size(min = 8, max = 32, message = "パスワードは8〜32文字で入力してください。")
    private String newPasswordConfirm;
}
