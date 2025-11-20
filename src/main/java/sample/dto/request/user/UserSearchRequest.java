package sample.dto.request.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

/** ユーザー検索リクエスト */
@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
