package dns.helper.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private static final String PHONE_NUMBER_PATTERN = "^\\d{3}$";
    private static final Pattern PATTERN = Pattern.compile(PHONE_NUMBER_PATTERN);

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context){
        return (validatePhoneNumber(phone));
    }

    private boolean validatePhoneNumber(String phone) {
        Matcher matcher = PATTERN.matcher(phone);
        return matcher.matches();
    }
}
