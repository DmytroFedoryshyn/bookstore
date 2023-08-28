package bookstore.validation;

import bookstore.dto.user.UserRegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof UserRegistrationRequest) {
            UserRegistrationRequest userRequestDto = (UserRegistrationRequest) value;
            return Objects.equals(userRequestDto.getPassword(), userRequestDto.getRepeatPassword());
        }
        return false;
    }
}
