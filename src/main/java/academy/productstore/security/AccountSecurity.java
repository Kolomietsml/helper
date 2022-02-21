package academy.productstore.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AccountSecurity {

    public boolean checkAccountId(Authentication authentication, Long accountId) {
        if (authentication.getPrincipal().toString().equals("anonymousUser")) {
            return false;
        }
        var id = authentication.getPrincipal().toString();
        return Long.valueOf(id).equals(accountId);
    }
}
