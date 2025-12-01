package sample.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Data;

/** パスワードリセット情報エンティティ */
@Data
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "users")
public class PasswordResetInfo {
    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ユーザーID */
    private Long usersId;

    /** トークン */
    private String token;

    /** 有効期限 */
    private String expiresAt;

    /** 使用済みフラグ */
    private Boolean used;

    /** 登録日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;
}
