package sample.dto.user;

import lombok.Data;

@Data
public class UserSearchDto {
    /** ユーザーID */
    private String userId;

    /** ユーザー名 */
    private String username;

    /** メールアドレス */
    private String email;

    /** 権限 */
    private int role;
}
