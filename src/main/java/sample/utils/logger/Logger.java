package sample.utils.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sample.dto.request.log.LogWriteRequest;
import sample.dto.response.SuccessResponse;
import sample.service.LogService;
import sample.types.log.LogExecType;
import sample.types.log.LogResultType;
import sample.utils.SecurityUtils;

/** ログ出力 */
@Aspect
@Component
@RequiredArgsConstructor
public class Logger {
    /** ログサービスDI */
    private final LogService logService;

    /**
     * ログ出力アノテーション
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Loggable {
        String value() default "";

        LogExecType execType() default LogExecType.OTHER;
    }

    /**
     * ログ先行書込み処理
     * ログの書き込みはWriteAheadLoggingに準拠し、処理の開始前にログを書き込む。
     * 
     * @param joinPoint JoinPointオブジェクト
     * @param loggable  ログ出力アノテーション
     */
    @Before("@annotation(loggable)")
    public void startWriteLog(Loggable loggable) {
        try {
            String executorId = SecurityUtils.getClaimValueOrNull("userId");
            // バッチなどで実行者IDを取得しない場合は「実行種別-処理名」で設定
            if (executorId == null || executorId.isEmpty()) {
                executorId = LogExecType.fromValue(loggable.execType().getValue()) + "-" + loggable.value();
            }
            String processName = loggable.value();
            int execType = loggable.execType().getValue();
            int result = LogResultType.PROCESSING.getValue();
            // ログ書込みリクエスト設定
            LogWriteRequest request = new LogWriteRequest();
            request.setExecutorId(executorId);
            request.setProcessName(processName);
            request.setExecType(execType);
            request.setResult(result);
            request.setStartedAt(LocalDateTime.now());

            Long logId = logService.startWriteLog(request);

            LoggerContext.setLogId(logId);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * ログ書込み処理（成功）
     * 
     * @param joinPoint JoinPointオブジェクト
     * @param loggable  ログ出力アノテーション
     */
    @AfterReturning(value = "@annotation(loggable)", returning = "result")
    public void endWriteLog(Loggable loggable, SuccessResponse result) {
        try {
            Long logId = LoggerContext.getLogId();
            int resultType = LogResultType.SUCCESS.getValue();
            // ログ書込みパラメータ設定
            LogWriteRequest request = new LogWriteRequest();
            request.setId(logId);
            request.setResult(resultType);
            request.setMessage(result.getMessage());

            logService.endWriteLog(null);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * ログ書込み処理（失敗）
     * 
     * @param joinPoint JoinPointオブジェクト
     * @param loggable  ログ出力アノテーション
     */
    @AfterThrowing(value = "@annotation(loggable)", throwing = "ex")
    public void endWriteLog(Loggable loggable, RuntimeException ex) {
        try {
            Long logId = LoggerContext.getLogId();
            int resultType = LogResultType.FAILURE.getValue();
            String message = ex.getMessage();
            if (message != null && message.length() > 1000) {
                message = message.substring(0, 1000) + "...";
            }
            // ログ書込みパラメータ設定
            LogWriteRequest request = new LogWriteRequest();
            request.setId(logId);
            request.setResult(resultType);
            request.setMessage(message);
            request.setCompletedAt(LocalDateTime.now());

            logService.endWriteLog(request);
        } catch (Exception e) {
            throw e;
        }
    }
}
