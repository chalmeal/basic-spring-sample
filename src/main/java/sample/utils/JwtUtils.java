package sample.utils;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/** JWTユーティリティ */
@Component
public class JwtUtils {
    /** アクセストークンシークレットキー */
    @Value("${spring.jwt.access-token.secret}")
    private String accessTokenSecret;

    /**
     * JWTを生成する
     * 
     * @param claims     クレーム
     * @param expiration 有効期限（秒）
     * @return 生成されたJWT
     */
    public String generateJwt(Map<String, Object> claims, long expiration) {
        Instant now = Instant.now();
        SecretKey secretKey = Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expiration)))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

}
