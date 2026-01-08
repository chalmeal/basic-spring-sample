package sample.repository.query.log;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/** ログ終了書込みクエリパラメータ */
@Data
@Builder
public class LogEndWriteParam {
    /** ログID */
    private Long id;

    /** 結果 */
    private int result;

    /** メッセージ */
    private String message;

    /** 終了日時 */
    private LocalDateTime completedAt;
}
