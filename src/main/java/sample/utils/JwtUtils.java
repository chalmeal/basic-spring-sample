package sample.utils;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import sample.utils.exception.JwtException;

/** JWTユーティリティ */
@Component
public class JwtUtils {
    /** アクセストークンシークレットキー */
    @Value("${spring.jwt.access-token.secret}")
    private String accessTokenSecret;
    /** アクセストークン有効期限（秒） */
    @Value("${spring.jwt.access-token.expiration}")
    private long accessTokenExpiration;

    private SecretKey getAccessTokenSecretKey() {
        return Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * JWTを生成する
     * 
     * @param claims クレーム
     * @return 生成されたJWT
     * @throws JwtException JWTの生成に失敗した場合
     */
    public String generateJwt(Map<String, Object> claims) throws JwtException {
        try {
            Instant now = Instant.now();

            return Jwts.builder()
                    .claims(claims)
                    .issuedAt(Date.from(now))
                    .expiration(Date.from(now.plusSeconds(accessTokenExpiration)))
                    .signWith(this.getAccessTokenSecretKey())
                    .compact();
        } catch (Exception e) {
            throw new JwtException("JWTの生成に失敗しました。", e);
        }
    }

    /**
     * アプリケーションのクレームを作成する
     * 
     * @param userId   ユーザーID
     * @param username ユーザー名
     * @param role     役割
     * @return クレームのマップ
     */
    public Map<String, Object> toClaims(String userId, String username, String role) {
        return Map.of(
                "userId", userId,
                "username", username,
                "role", role);
    }

    /**
     * JWTのクレームを解析する
     * 
     * @param token JWTトークン
     * @return クレーム
     * @throws JwtException JWTの解析に失敗した場合
     */
    public Claims parseClaims(String token) throws JwtException {
        try {
            return Jwts.parser()
                    .verifyWith(this.getAccessTokenSecretKey()) // 署名検証
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new JwtException("JWTの解析に失敗しました。", e);
        }
    }

    /**
     * JWTからクレームの値を取得する
     * 
     * @param key クレームのキー
     * @return クレームの値
     * @throws JwtException クレームが存在しない場合
     */
    public static String getClaimValue(String key) throws JwtException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = (Claims) auth.getDetails();
        if (claims == null || !claims.containsKey(key)) {
            throw new JwtException("JWTに指定されたクレームが存在しません。");
        }

        return claims.get(key, String.class);
    }

}
