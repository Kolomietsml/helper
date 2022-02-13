package academy.productstore.validation;

import academy.productstore.dto.request.RegistrationRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        var accountDTO = (RegistrationRequest) o;
        return accountDTO.getPassword().equals(accountDTO.getPasswordConfirm());
    }
}