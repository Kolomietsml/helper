package dns.helper.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    EmailValidator validator = new EmailValidator();

    @Test
    public void whenEmailMatches_thanCorrect() {
        assertTrue(validator.isValid("username@domain.com", null));
    }

    @Test
    public void whenEmailDoesNotMatches_thanNotCorrect() {
        assertFalse(validator.isValid("username_domain.com", null));
        assertFalse(validator.isValid("username@domain", null));
    }
}