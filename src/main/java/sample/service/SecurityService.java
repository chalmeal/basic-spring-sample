package sample.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Service;

/** パスワードハッシュサービス */
@Service
public class SecurityService {

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
    public boolean verify(String loginPassword, String hashedPassword) {
        String loginHashPassword = hashPassword(loginPassword);

        return MessageDigest.isEqual(
                loginHashPassword.getBytes(StandardCharsets.UTF_8),
                hashedPassword.getBytes(StandardCharsets.UTF_8));
    }

}
