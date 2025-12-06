package sample.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.dto.request.auth.LoginRequest;
import sample.dto.response.auth.LoginResponse;
import sample.entity.User;
import sample.repository.AuthRepository;
import sample.repository.UserRepository;
import sample.types.user.UserRoleType;
import sample.utils.JwtUtils;
import sample.utils.exception.UnAuthorizedException;

/** 認証サービス */
@Service
@RequiredArgsConstructor
public class AuthService {
    /** セキュリティサービスDI */
    private final SecurityService securityService;
    /** 認証リポジトリDI */
    private final AuthRepository authRepository;
    /** ユーザーリポジトリDI */
    private final UserRepository userRepository;
    /** JWTユーティリティDI */
    private final JwtUtils jwtUtils;

    /**
     * ログイン処理
     * 
     * @param request ログインリクエスト
     */
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new UnAuthorizedException("メールアドレスまたはパスワードが違います。"));

        String hashedPassword = authRepository.getHashPasswordByEmail(request.getEmail());
        if (!securityService.verifyPassword(request.getPassword(), hashedPassword)) {
            // 認証失敗時の処理
            throw new UnAuthorizedException("メールアドレスまたはパスワードが違います。");
        }

        String role = UserRoleType.fromValue(user.getRole());
        Map<String, Object> claims = jwtUtils.toClaims(
                user.getUserId(), user.getUsername(), role);
        String accessToken = jwtUtils.generateJwt(claims);

        return new LoginResponse(accessToken);
    }
}
