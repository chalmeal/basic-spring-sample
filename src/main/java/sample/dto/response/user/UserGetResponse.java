package sample.dto.response.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import sample.entity.User;

/** ユーザー取得レスポンス */
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserGetResponse {
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

    /** 状態 */
    private int status;

    /** 登録日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;

    /** 削除日時 */
    private LocalDateTime deletedAt;

    public UserGetResponse(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.status = user.getStatus();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.deletedAt = user.getDeletedAt();
    }
}
