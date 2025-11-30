package sample.utils.constrains;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/** アクセス権限のバリデーションアノテーション */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidAccessImpl.class)
public @interface ValidAccess {
    String value();

    String message() default "対象のリソースにアクセスが認可されていません。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
