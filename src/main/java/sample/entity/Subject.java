package sample.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Data;

/** 科目エンティティ */
@Data
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "subjects")
public class Subject {
    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 科目名 */
    private String name;

    /** 登録日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;

    /** 削除日時 */
    private LocalDateTime deletedAt;
}
