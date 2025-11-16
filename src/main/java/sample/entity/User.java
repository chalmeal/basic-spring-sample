package sample.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.jdbc.entity.NamingType;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

/** ユーザーエンティティ */
@Data
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "users")
public class User {
    /** ID */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ユーザーID */
    private String userId;

    /** ユーザー名 */
    private String username;

    /** メールアドレス */
    private String email;

    /** パスワード */
    private String password;

    /** 権限 */
    private int role;

    /** 登録日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;

    /** 削除日時 */
    private LocalDateTime deletedAt;
}
