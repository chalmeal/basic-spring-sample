package sample.utils.constrains;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import io.jsonwebtoken.Claims;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import sample.types.user.UserRoleType;

/** アクセス権限のバリデーション実装 */
@RequiredArgsConstructor
public class ValidAccessImpl implements ConstraintValidator<ValidAccess, String> {

    private String claimKey;

    @Override
    public void initialize(ValidAccess annotation) {
        this.claimKey = annotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }

        Claims claims = (Claims) auth.getDetails();
        if (claims == null || !claims.containsKey(claimKey)) {
            return false;
        }

        // 管理者権限は全てのリソースにアクセス可能
        String role = claims.get("role", String.class);
        if (UserRoleType.ADMIN.getRoleName().equals(role)) {
            return true;
        }

        Object claimValue = claims.get(claimKey);

        return value.equals(claimValue);
    }
}
