package sample.service.security;

import lombok.Builder;
import lombok.Data;

/** JWTクレーム定義クラス */
@Data
@Builder
public class JwtClaim {
    /** ユーザーID */
    private String userId;

    /** ユーザー名 */
    private String username;

    /** 権限 */
    private String role;
}
