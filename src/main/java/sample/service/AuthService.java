package sample.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.dto.request.auth.LoginPasscodeRequest;
import sample.dto.request.auth.LoginRequest;
import sample.dto.response.auth.LoginResponse;
import sample.entity.MFAPasscodeInfo;
import sample.entity.User;
import sample.repository.AuthRepository;
import sample.repository.UserRepository;
import sample.repository.query.auth.MFAPasscodeInfoCreateParam;
import sample.types.user.UserRoleType;
import sample.utils.JwtUtils;
import sample.utils.MailUtils;
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
    /** JWTユーティリティDI */ // TODO: DIしない
    private final JwtUtils jwtUtils;
    /** メール送信ユーティリティDI */ // TODO: DIしない
    private final MailUtils mailUtils;
    /** パスコード有効期間（分） */
    @Value("${spring.security.mfa.passcode.expiration}")
    private int PASSCODE_EXPIRATION_MINUTES;

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

        // TODO: アクセストークン発行処理の共通化
        String role = UserRoleType.fromValue(user.getRole());
        Map<String, Object> claims = jwtUtils.toClaims(
                user.getUserId(), user.getUsername(), role);
        String accessToken = jwtUtils.generateJwt(claims);

        return new LoginResponse(accessToken);
    }

    /**
     * パスコード送信処理（MFA）
     * 
     * @param request ログインリクエスト
     */
    @Transactional
    public void sendPasscode(LoginRequest request) {
        User user = userRepository.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new UnAuthorizedException("メールアドレスまたはパスワードが違います。"));

        String hashedPassword = authRepository.getHashPasswordByEmail(request.getEmail());
        if (!securityService.verifyPassword(request.getPassword(), hashedPassword)) {
            // 認証失敗時の処理
            throw new UnAuthorizedException("メールアドレスまたはパスワードが違います。");
        }

        // パスコード生成・保存
        int passcode = new SecureRandom().nextInt(1_000_000);
        String passcodeStr = String.format("%06d", passcode);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(PASSCODE_EXPIRATION_MINUTES);
        MFAPasscodeInfoCreateParam param = MFAPasscodeInfoCreateParam.builder()
                .userId(user.getUserId())
                .passcode(passcodeStr)
                .expiresAt(expiresAt)
                .build();
        authRepository.createPasscode(param);

        String body = String.format("""
                ログインパスコードのお知らせ\n
                \n
                以下のパスコードを入力してください。パスコードの有効期間は発行から5分です。\n
                %s
                """, passcodeStr);
        mailUtils.sendMail(MailUtils.MailSenderObject.builder()
                .to(request.getEmail())
                .subject("ログインパスコードのお知らせ")
                .body(body)
                .build());
    }

    /**
     * パスコード検証処理（MFA）
     * 
     * @param request ログインパスコードリクエスト
     * @return ログインレスポンス
     */
    @Transactional
    public LoginResponse verifyPasscode(LoginPasscodeRequest request) {
        User user = userRepository.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new UnAuthorizedException("メールアドレスまたはパスワードが違います。"));

        // パスコードの検証ロジックをここに追加する必要があります
        MFAPasscodeInfo passcodeInfo = authRepository.getPasscodeByUserId(user.getUserId());
        if (passcodeInfo == null || !request.getPasscode().equals(passcodeInfo.getPasscode())) {
            // 認証失敗時の処理
            throw new UnAuthorizedException("入力されたパスコードが違います。");
        } else if (passcodeInfo.getExpiresAt().isBefore(LocalDateTime.now())) {
            // 有効期限切れの処理
            authRepository.deletePasscodeInfo(user.getUserId());
            throw new UnAuthorizedException("入力されたパスコードの有効期限が切れています。パスコードを再発行してください。");
        }

        // パスコード情報を削除
        authRepository.deletePasscodeInfo(user.getUserId());

        // TODO: アクセストークン発行処理の共通化
        String role = UserRoleType.fromValue(user.getRole());
        Map<String, Object> claims = jwtUtils.toClaims(
                user.getUserId(), user.getUsername(), role);
        String accessToken = jwtUtils.generateJwt(claims);

        return new LoginResponse(accessToken);
    }
}