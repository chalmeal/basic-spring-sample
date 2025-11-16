package sample.dto.user;

import java.time.LocalDateTime;

import lombok.Data;
import sample.entity.User;

/** ユーザー取得レスポンス */
@Data
public class UserGetDto {
    /** ID */
    private Long id;

    /** ユーザーID */
    private String userId;

    /** ユーザー名 */
    private String name;

    /** メールアドレス */
    private String email;

    /** 権限 */
    private int role;

    /** 登録日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;

    /** 削除日時 */
    private LocalDateTime deletedAt;

    public UserGetDto(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.deletedAt = user.getDeletedAt();
    }
}
