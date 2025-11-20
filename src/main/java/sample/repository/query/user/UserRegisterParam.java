package sample.repository.query.user;

import lombok.Builder;
import lombok.Getter;

/** ユーザー登録クエリパラメータ */
@Getter
@Builder
public class UserRegisterParam {
    /** ID */
    private Long id;

    /** ユーザーID */
    private String userId;

    /** ユーザー名 */
    private String username;

    /** パスワード */
    private String password;

    /** 権限 */
    private int role;

    /** 状態 */
    private int status;
}
