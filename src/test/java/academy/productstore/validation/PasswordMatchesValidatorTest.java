package academy.productstore.validation;

import academy.productstore.web.dto.request.CreateAccountDTO;
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

    private CreateAccountDTO createTestObject(String password, String passwordConfirm) {
        var account = new CreateAccountDTO();
        account.setFirstname("FirstName");
        account.setLastname("LastName");
        account.setEmail("test@mail.com");
        account.setPhone("000000000");
        account.setPassword(password);
        account.setPasswordConfirm(passwordConfirm);
        return account;
    }
}