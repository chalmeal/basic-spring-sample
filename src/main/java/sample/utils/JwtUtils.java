package sample.utils;

import java.sql.Date;
import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

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

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expiration)))
                .signWith(Jwts.SIG.HS512.key().build())
                .compact();
    }

}
