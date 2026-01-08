package sample.repository.query.log;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Data;

/** ログ先行書込みクエリパラメータ */
@Entity
@Table(name = "logs")
@Data
public class LogStartWriteParam {
    /** ログID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 実行者ID */
    @Column(name = "executor_id")
    private String executorId;

    /** 処理名 */
    @Column(name = "process_name")
    private String processName;

    /** 実行種別 */
    @Column(name = "exec_type")
    private int execType;

    /** 結果 */
    @Column(name = "result")
    private int result;

    /** 開始日時 */
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    /** 終了日時 */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}
