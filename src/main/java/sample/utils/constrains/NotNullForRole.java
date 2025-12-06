package sample.utils.constrains;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sample.types.user.UserRoleType;

/** 対象権限必須バリデーションアノテーション */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullForRoleImpl.class)
public @interface NotNullForRole {
    UserRoleType[] roles();

    String message() default "値は必須です。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
