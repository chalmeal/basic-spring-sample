package sample.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.extern.slf4j.Slf4j;
import sample.dto.response.ErrorResponse;
import sample.utils.exception.UnAuthorizedException;

/** コントローラー共通ユーティリティ */
@ControllerAdvice
@Slf4j
public class RestErrorAdvice {

    /**
     * バリデーションエラー処理(400)
     * 
     * @param ex 例外情報
     * @return 400エラーレスポンス
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * リクエストパラメータエラー処理(400)
     * 
     * @param ex 例外情報
     * @return 400エラーレスポンス
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleMethodValidationException(HandlerMethodValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getAllErrors().get(0)
                        .getDefaultMessage()));
    }

    /**
     * リクエストパラメータ不足エラー処理(400)
     * 
     * @param ex 例外情報
     * @return 400エラーレスポンス
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(String.format("%s は必須です。", ex.getParameterName())));
    }

    /**
     * 認証エラー処理(401)
     * 
     * @param ex 例外情報
     * @return 401エラーレスポンス
     */
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<?> handleUnauthorized(UnAuthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(ex.getMessage()));
    }

    /**
     * 権限認可エラー処理(403)
     * 
     * @param ex 例外情報
     * @return 403エラーレスポンス
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ex.getMessage()));
    }

    /**
     * リソース未検出エラー処理(404)
     * 
     * @param ex 例外情報
     * @return 404エラーレスポンス
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("リソースが見つかりません。"));
    }

    /**
     * サーバーエラー処理(500)
     * 
     * @param ex 例外情報
     * @return 500エラーレスポンス
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ex.getMessage()));
    }

}
