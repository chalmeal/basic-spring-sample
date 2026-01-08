package sample.dto.request.log;

import java.time.LocalDateTime;

import lombok.Data;

/** ログ書込みリクエスト */
@Data
public class LogWriteRequest {
    /** ログID */
    private Long id;

    /** 実行者ID */
    private String executorId;

    /** 処理名 */
    private String processName;

    /** 実行種別 */
    private Integer execType;

    /** 結果 */
    private Integer result;

    /** メッセージ */
    private String message;

    /** 開始日時 */
    private LocalDateTime startedAt;

    /** 終了日時 */
    private LocalDateTime completedAt;
}
