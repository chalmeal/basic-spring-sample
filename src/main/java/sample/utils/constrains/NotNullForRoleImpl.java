package sample.utils.constrains;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sample.types.user.UserRoleType;
import sample.utils.SecurityUtils;

/** 対象権限必須バリデーション実装 */
public class NotNullForRoleImpl implements ConstraintValidator<NotNullForRole, String> {
    private UserRoleType[] roles;
    private String name;
    private String message;

    @Override
    public void initialize(NotNullForRole constraintAnnotation) {
        this.roles = constraintAnnotation.roles();
        this.name = constraintAnnotation.name();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // ユーザーの権限を取得
        String currentRole = SecurityUtils.getClaimValue("role");

        // 対象権限でなければバリデーション対象外
        boolean shouldValidate = Arrays.stream(roles)
                .anyMatch(role -> role.getRoleName().equals(currentRole));
        if (!shouldValidate) {
            return true;
        }

        // 対象者なら必須チェック
        if (value == null || value.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format(message, name))
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
