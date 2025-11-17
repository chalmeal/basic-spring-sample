package sample.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

/** ユーザーエンティティ */
@Data
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "users")
public class User {
    /** ID */
    @Id
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

    /** 状態 */
    private int status;

    /** 登録日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;

    /** 削除日時 */
    private LocalDateTime deletedAt;

    /** 権限定数 */
    public enum Role {
        ADMIN(0),
        USER(1);

        private final int value;

        Role(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /** 状態定数 */
    public enum Status {
        TEMPORARY(0),
        REGISTERED(1);

        private final int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
