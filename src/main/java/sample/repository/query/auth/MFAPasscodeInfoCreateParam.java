package sample.repository.query.auth;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

/** MFAパスコード情報作成パラメータ */
@Getter
@Builder
public class MFAPasscodeInfoCreateParam {
    /** ユーザーID */
    private String userId;

    /** パスコード */
    private String passcode;

    /** 有効期限 */
    private LocalDateTime expiresAt;
}
