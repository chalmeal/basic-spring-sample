package sample.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import sample.utils.exception.JwtException;

/** セキュリティユーティリティ */
@Component
public class SecurityUtils {
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
