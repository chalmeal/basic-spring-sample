package sample.query.user;

import lombok.Builder;

/** ユーザー仮登録パラメータ */
@Builder
public class UserRegisterTemporaryParam {
    /** メールアドレス */
    private String email;

    /** 状態 */
    private int status;
}
