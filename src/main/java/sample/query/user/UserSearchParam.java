package sample.query.user;

import lombok.Builder;
import lombok.Data;

/** ユーザー検索クエリパラメータ */
@Data
@Builder
public class UserSearchParam {
    /** ユーザーID */
    private String userId;

    /** ユーザー名 */
    private String username;

    /** メールアドレス */
    private String email;

    /** 権限 */
    private Integer role;
}
