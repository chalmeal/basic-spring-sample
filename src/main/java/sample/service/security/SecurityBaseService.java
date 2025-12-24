package sample.service.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import sample.utils.exception.JwtException;

/** セキュリティサービスの基盤実装 */
public class SecurityBaseService {
    /** アクセストークンシークレットキー */
    @Value("${spring.jwt.access-token.secret}")
    private String accessTokenSecret;
    /** アクセストークン有効期限（秒） */
    @Value("${spring.jwt.access-token.expiration}")
    private long accessTokenExpiration;
    // ObjectMapperインスタンス
    private ObjectMapper objectMapper = new ObjectMapper();

    // アクセストークンシークレットキー取得
    private SecretKey getAccessTokenSecretKey() {
        return Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * JWTクレーム定義
     * 
     * @param userId   ユーザーID
     * @param username ユーザー名
     * @param role     役割
     * @return クレームのマップ
     */
    protected Map<String, Object> toClaims(String userId, String username, String role) {
        return Map.of(
                "userId", userId,
                "username", username,
                "role", role);
    }

    /**
     * アクセストークンを取得する
     * 
     * @param claims JWTクレーム
     * @return アクセストークン
     */
    protected String getAccessToken(JwtClaim claims) {
        try {
            Instant now = Instant.now();
            Map<String, Object> claimsMap = objectMapper.convertValue(claims, Map.class);

            return Jwts.builder()
                    .claims(claimsMap)
                    .issuedAt((Date.from(now)))
                    .expiration(Date.from(now.plusSeconds(accessTokenExpiration)))
                    .signWith(this.getAccessTokenSecretKey())
                    .compact();
        } catch (Exception e) {
            throw new JwtException("アクセストークンの取得に失敗しました。", e);
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

}
