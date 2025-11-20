package sample.repository.query.user;

import lombok.Builder;
import lombok.Getter;

/** ユーザー検索クエリパラメータ */
@Getter
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

    /** ページサイズ */
    private Integer pageSize;

    /** ページ番号 */
    private Integer pageNumber;
}
