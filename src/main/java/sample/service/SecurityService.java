package sample.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sample.dto.request.auth.PasswordChangeLoginAfterRequest;
import sample.dto.request.auth.PasswordChangeRequest;
import sample.entity.PasswordResetInfo;
import sample.entity.User;
import sample.repository.AuthRepository;
import sample.repository.UserRepository;
import sample.utils.DateUtils;
import sample.utils.MailUtils;
import sample.utils.exception.NotFoundException;
import sample.utils.exception.UnAuthorizedException;

/** パスワードハッシュサービス */
@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {
    /** ユーザーリポジトリDI */
    private final UserRepository userRepository;
    /** 認証リポジトリDI */
    private final AuthRepository authRepository;
    /** メール送信ユーティリティDI */
    private final MailUtils mailUtils;
    /** パスワードリセットリンク */
    @Value("${spring.mail.properties.password-reset-link}")
    private String resetLink;

    /**
     * パスワードリセット（未ログイン）
     * 
     * @param userId ユーザーID
     */
    public void resetPassword(String email) {
        Optional<User> user = userRepository.getByEmail(email);
        if (user.isEmpty()) {
            log.warn("未登録のメールアドレスでパスワードリセット要求がありました。： {}", email);
            throw new NotFoundException();
        }

        String token = UUID.randomUUID().toString();
        String expirationAt = LocalDateTime.now().plusMinutes(30).toString();
        authRepository.createPasswordResetInfo(user.get().getId(), token, expirationAt);

        String body = String.format("""
                パスワードがリセットされました。\n
                下記のリンクから新しいパスワードを設定してください。\n
                %s?token=%s
                """, resetLink, token);
        mailUtils.sendMail(MailUtils.MailSenderObject.builder()
                .to(email)
                .subject("パスワードリセットのお知らせ")
                .body(body)
                .build());
    }

    /**
     * パスワード変更（ログイン前）
     * 
     * @param request パスワード変更リクエスト
     */
    public void changePassword(PasswordChangeRequest request) {
        PasswordResetInfo passwordResetInfo = authRepository.getPasswordResetInfoByToken(request.getToken())
                .orElseThrow(() -> new NotFoundException("無効なパスワードリセットトークンです。"));
        // 確認用パスワードと新しいパスワードの一致チェック
        if (!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
            throw new IllegalArgumentException("新しいパスワードと確認用パスワードが一致しません。");
        }
        // トークンの有効性チェック
        if (passwordResetInfo.getUsed()
                || DateUtils.parseDateTime(passwordResetInfo.getExpiresAt()).isBefore(LocalDateTime.now())) {
            throw new NotFoundException("リンクが無効です。再度パスワードリセットを行ってください。");
        }

        // パスワードリセット情報を使用済みに設定
        authRepository.updatePasswordResetInfoAsUsed(passwordResetInfo.getId());

        // パスワード更新
        String newPassword = hashPassword(request.getNewPassword());
        authRepository.updatePassword(passwordResetInfo.getUsersId(), newPassword);
    }

    /**
     * パスワード変更（ログイン後）
     * 
     * @param request パスワード変更リクエスト
     * @param userId  ユーザーID
     */
    public void changePassword(PasswordChangeLoginAfterRequest request, String userId) {
        // 確認用パスワードと新しいパスワードの一致チェック
        if (!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
            throw new IllegalArgumentException("新しいパスワードと確認用パスワードが一致しません。");
        }

        // 現在のパスワード認証
        String hashedPassword = authRepository.getHashPasswordByUserId(userId);
        if (!verifyPassword(request.getCurrentPassword(), hashedPassword)) {
            // 認証失敗時の処理
            throw new UnAuthorizedException("現在のパスワードが違います。");
        }

        // パスワード更新
        String newPassword = hashPassword(request.getNewPassword());
        authRepository.updatePasswordByUserId(userId, newPassword);
    }

    /**
     * パスワードをハッシュ化(SHA-512)
     * 
     * @param password ハッシュ化するパスワード
     * @return ハッシュ化されたパスワード
     */
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 algorithm not available", e);
        }
    }

    /**
     * パスワードの検証
     * 
     * @param loginPassword  ログイン時に入力されたパスワード
     * @param hashedPassword DBに保存されているハッシュ化されたパスワード
     * @return パスワードが一致する場合はtrue、それ以外はfalse
     */
    public boolean verifyPassword(String loginPassword, String hashedPassword) {
        String loginHashPassword = hashPassword(loginPassword);

        return MessageDigest.isEqual(
                loginHashPassword.getBytes(StandardCharsets.UTF_8),
                hashedPassword.getBytes(StandardCharsets.UTF_8));
    }

}
