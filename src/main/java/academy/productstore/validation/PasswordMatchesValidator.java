package academy.productstore.validation;

import academy.productstore.web.dto.CreateAccountDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        var accountDTO = (CreateAccountDTO) o;
        return accountDTO.getPassword().equals(accountDTO.getPasswordConfirm());
    }
}