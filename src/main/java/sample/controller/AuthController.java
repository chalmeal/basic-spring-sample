package sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.dto.request.auth.LoginRequest;
import sample.dto.request.auth.PasswordChangeRequest;
import sample.dto.request.auth.PasswordResetRequest;
import sample.dto.response.ErrorResponse;
import sample.service.AuthService;
import sample.service.SecurityService;
import sample.utils.exception.NotFoundException;
import sample.utils.exception.UnAuthorizedException;

/** 認証コントローラ */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    /** 認証サービスDI */
    private final AuthService authService;
    /** セキュリティサービスDI */
    private final SecurityService securityService;

    /**
     * ログイン処理
     * 
     * @param request ログインリクエスト
     * @return レスポンスエンティティ
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        try {
            return ResponseEntity.ok().body(authService.login(request));
        } catch (UnAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * パスワードリセット（未ログイン）
     * 
     * @param email メールアドレス
     * @return
     */
    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetRequest request) {
        try {
            securityService.resetPassword(request.getEmail());
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            // セキュリティの観点から、ユーザーに明示的にエラーとはしない
            return ResponseEntity.ok().build();
        } catch (MailSendException e) {
            // メール送信に失敗
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * パスワード変更（未ログイン）
     * 
     * @param request パスワード変更リクエスト
     * @return レスポンスエンティティ
     */
    @PostMapping("/password/change")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangeRequest request) {
        try {
            securityService.changePassword(request);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            // 無効なパスワードリセットリクエスト
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (IllegalArgumentException e) {
            // 確認用パスワードと新しいパスワードが一致しない場合
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }
}
