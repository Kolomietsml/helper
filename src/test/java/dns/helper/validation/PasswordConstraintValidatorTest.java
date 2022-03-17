package dns.helper.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordConstraintValidatorTest {

    PasswordConstraintValidator validator = new PasswordConstraintValidator();

    @Test
    public void whenMinLengthMatches_thenCorrect() {
        assertTrue(validator.isValid("1234567", null));
    }

    @Test
    public void whenMaxLengthMatches_thenNotCorrect() {
        assertTrue(validator.isValid("1234567890123456789", null));
    }

    @Test
    public void whenPasswordShorter_thenNotCorrect() {
        assertFalse(validator.isValid("123456", null));
    }

    @Test
    public void whenPasswordLonger_thenNotCorrect() {
        assertFalse(validator.isValid("12345678901234567890", null));
    }
}