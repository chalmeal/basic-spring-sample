package sample.repository.query.user;

import lombok.Builder;
import lombok.Getter;

/** ユーザー仮登録クエリパラメータ */
@Getter
@Builder
public class UserRegisterTemporaryParam {
    /** メールアドレス */
    private String email;

    /** 状態 */
    private int status;
}
