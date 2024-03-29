package dns.helper.validation;

import dns.helper.api.dto.request.RegistrationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordMatchesValidatorTest {

    PasswordMatchesValidator validator = new PasswordMatchesValidator();

    @Test
    public void whenPasswordsMatches_thenCorrect() {
        var account = createTestObject("12345678", "12345678");
        assertTrue(validator.isValid(account, null));
    }

    @Test
    public void whenPasswordsDontMatches_thenNotCorrect() {
        var account = createTestObject("123456789", "1234567890");
        assertFalse(validator.isValid(account, null));
    }

    private RegistrationRequest createTestObject(String password, String passwordConfirm) {
        var account = new RegistrationRequest();
        account.setEmail("test@mail.com");
        account.setPassword(password);
        account.setPasswordConfirm(passwordConfirm);
        return account;
    }
}