package dns.helper.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberValidatorTest {

    PhoneNumberValidator validator = new PhoneNumberValidator();

    @Test
    public void whenMatchesNineDigitsNumber_thenCorrect() {
        assertTrue(validator.isValid("112", null));
    }

    @Test
    public void whenMoreThanNineDigits_thenNotCorrect() {
        assertFalse(validator.isValid("1234", null));
    }

    @Test
    public void whenLessThanNineDigits_thenNotCorrect() {
        assertFalse(validator.isValid("11", null));
    }

}