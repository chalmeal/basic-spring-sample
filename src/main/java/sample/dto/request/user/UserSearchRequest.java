package sample.dto.request.user;

import lombok.Builder;
import lombok.Getter;

/** ユーザー検索リクエスト */
@Getter
@Builder
public class UserSearchRequest {
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
