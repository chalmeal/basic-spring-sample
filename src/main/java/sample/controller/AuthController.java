package sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.dto.request.auth.LoginRequest;
import sample.dto.response.ErrorResponse;
import sample.service.AuthService;
import sample.utils.exception.UnAuthorizedException;

/** 認証コントローラ */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    /** 認証サービスDI */
    private final AuthService authService;

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
}
