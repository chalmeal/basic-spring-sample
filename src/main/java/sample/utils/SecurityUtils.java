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

    /**
     * JWTからクレームの値を取得する（存在しない場合はnullを返す）
     * 
     * @param key クレームのキー
     * @return クレームの値またはnull
     */
    public static String getClaimValueOrNull(String key) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Claims claims = (Claims) auth.getDetails();
        if (claims == null || !claims.containsKey(key)) {
            return null;
        }

        return claims.get(key, String.class);
    }

}
