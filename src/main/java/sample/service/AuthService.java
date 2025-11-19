package sample.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sample.dto.request.auth.LoginRequest;
import sample.dto.response.auth.LoginResponse;
import sample.entity.User;
import sample.repository.AuthRepository;
import sample.repository.UserRepository;
import sample.utils.JwtUtils;
import sample.utils.exception.NotFoundException;
import sample.utils.exception.UnAuthorizedException;

/** 認証サービス */
@Service
@RequiredArgsConstructor
public class AuthService {
    /** パスワードハッシュサービスDI */
    private final PasswordHashService passwordHashService;
    /** 認証リポジトリDI */
    private final AuthRepository authRepository;
    /** ユーザーリポジトリDI */
    private final UserRepository userRepository;
    /** JWTユーティリティDI */
    private final JwtUtils jwtUtils;
    /** アクセストークン有効期限（秒） */
    @Value("${spring.jwt.access-token.expiration}")
    private long accessTokenExpiration;

    /**
     * ログイン処理
     * 
     * @param request ログインリクエスト
     */
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.getByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("ユーザーが見つかりませんでした。", request.getEmail()));

        String hashedPassword = authRepository.getHashPassword(request.getEmail());
        if (!passwordHashService.verify(request.getPassword(), hashedPassword)) {
            // 認証失敗時の処理
            throw new UnAuthorizedException("メールアドレスまたはパスワードが違います。");
        }

        // アクセストークンのクレームを作成
        Map<String, Object> claims = Map.of(
                "userId", user.getUserId(),
                "username", user.getUsername(),
                "role", user.getRole());

        String accessToken = jwtUtils.generateJwt(claims, accessTokenExpiration);

        return new LoginResponse(accessToken);
    }
}
