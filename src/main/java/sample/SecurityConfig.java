package sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sample.dto.response.ErrorResponse;

/** セキュリティ設定 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthorizationFilter authorizationFilter)
            throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(authz -> {
            authz
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/user/register/**").permitAll()
                    .requestMatchers("/api/system/health").permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll();
        });
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(
                e -> {
                    e.authenticationEntryPoint((request, response, authException) -> {
                        ErrorResponse errorResponse = new ErrorResponse("認証に失敗しました。");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write(errorResponse.valueAsString());
                    });
                });

        return http.build();
    }

}