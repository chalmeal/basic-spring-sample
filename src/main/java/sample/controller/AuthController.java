package sample.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sample.dto.request.auth.LoginPasscodeRequest;
import sample.dto.request.auth.LoginRequest;
import sample.dto.request.auth.PasswordChangeRequest;
import sample.dto.request.auth.PasswordResetRequest;
import sample.service.AuthService;
import sample.service.SecurityService;

/** 認証コントローラ */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    /** 認証サービスDI */
    private final AuthService authService;
    /** セキュリティサービスDI */
    private final SecurityService securityService;
    /** 二要素認証有効フラグ */
    @Value("${spring.security.mfa.passcode.enabled}")
    private boolean mfaEnabled;

    /**
     * ログイン処理
     * 
     * @param request ログインリクエスト
     * @return レスポンスエンティティ
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        if (mfaEnabled) {
            // 二要素認証有効時はパスコード送信のみ実施
            authService.sendPasscode(request);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.ok().body(authService.login(request));
    }

    /**
     * パスコード認証処理
     * 
     * @param request パスコード認証リクエスト
     * @return レスポンスエンティティ
     */
    @PostMapping("/login/passcode")
    public ResponseEntity<?> loginPasscode(@RequestBody @Valid LoginPasscodeRequest request) {
        return ResponseEntity.ok().body(authService.verifyPasscode(request));
    }

    /**
     * パスワードリセット（未ログイン）
     * 
     * @param email メールアドレス
     * @return
     */
    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetRequest request) {
        securityService.resetPassword(request.getEmail());
        return ResponseEntity.ok().build();
    }

    /**
     * パスワード変更（未ログイン）
     * 
     * @param request パスワード変更リクエスト
     * @return レスポンスエンティティ
     */
    @PostMapping("/password/change")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangeRequest request) {
        securityService.changePassword(request);
        return ResponseEntity.ok().build();
    }
}
