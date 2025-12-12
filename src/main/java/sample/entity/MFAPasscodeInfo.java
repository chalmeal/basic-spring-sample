package sample.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Data;

/** MFAパスコード情報エンティティ */
@Data
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public class MFAPasscodeInfo {
    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ユーザーID */
    private String userId;

    /** パスコード */
    private String passcode;

    /** 有効期限 */
    private LocalDateTime expiresAt;
}
