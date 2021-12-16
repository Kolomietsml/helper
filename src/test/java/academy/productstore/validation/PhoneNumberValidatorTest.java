package academy.productstore.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneNumberValidatorTest {

    PhoneNumberValidator validator = new PhoneNumberValidator();

    @Test
    public void whenMatchesNineDigitsNumber_thenCorrect() {
        assertTrue(validator.isValid("123456789", null));
    }

    @Test
    public void whenMoreThanNineDigits_thenNotCorrect() {
        assertFalse(validator.isValid("1234567890", null));
    }

    @Test
    public void whenLessThanNineDigits_thenNotCorrect() {
        assertFalse(validator.isValid("12345678", null));
    }
}