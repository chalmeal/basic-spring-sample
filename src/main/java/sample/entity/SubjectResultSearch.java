package sample.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Data;

/** 科目別成績エンティティ */
@Data
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public class SubjectResultSearch {
    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ユーザー名 */
    private String userName;

    /** 科目名 */
    private String subjectName;

    /** 得点 */
    private Integer score;
}
