package sample.utils.constrains;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/** ファイル必須バリデーション実装 */
public class NotEmptyFileImpl implements ConstraintValidator<NotEmptyFile, MultipartFile> {
    private String message;

    @Override
    public void initialize(NotEmptyFile annotation) {
        this.message = annotation.message();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        // 必須チェック
        if (value == null || value.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}