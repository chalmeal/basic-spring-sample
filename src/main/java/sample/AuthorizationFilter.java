package sample;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sample.dto.response.ErrorResponse;
import sample.utils.exception.JwtException;

/** 認証認可フィルター設定 */
@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements Filter {
    /** アクセストークンシークレットキー */
    @Value("${spring.jwt.access-token.secret}")
    private String accessTokenSecret;

    // アクセストークンシークレットキー取得
    private SecretKey getAccessTokenSecretKey() {
        return Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            String authorizationHeader = httpRequest.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                chain.doFilter(request, response);
                return;
            }

            // トークンを解析してクレームを取得
            String token = authorizationHeader.replace("Bearer ", "");
            Claims claims = Jwts.parser()
                    .verifyWith(this.getAccessTokenSecretKey()) // 署名検証
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // クレームからユーザー情報を取得
            String userId = claims.get("userId", String.class);
            String username = claims.get("username", String.class);
            String role = claims.get("role", String.class);
            if (userId.isEmpty() || username.isEmpty() || role == null) {
                this.errorHandler(httpResponse,
                        HttpServletResponse.SC_FORBIDDEN,
                        "アクセストークンが不正です。");
                return;
            }
            List<GrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + role));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null,
                    authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            authentication.setDetails(claims);

            chain.doFilter(request, response);
        } catch (JwtException e) {
            // JWTの解析に失敗した場合の処理
            this.errorHandler(httpResponse,
                    HttpServletResponse.SC_FORBIDDEN,
                    "アクセストークンが不正です。");
        } catch (Exception e) {
            // その他の例外が発生した場合の処理
            this.errorHandler(httpResponse,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "サーバー内部でエラーが発生しました。");
        }
    }

    /**
     * エラーレスポンス
     * 
     * @param status       HTTPステータスコード
     * @param message      エラーメッセージ
     * @param httpResponse HTTPレスポンス
     */
    private void errorHandler(HttpServletResponse httpResponse, int status, String message) {
        try {
            ErrorResponse errorResponse = new ErrorResponse(message);
            httpResponse.setStatus(status);
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.getWriter().write(errorResponse.valueAsString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
