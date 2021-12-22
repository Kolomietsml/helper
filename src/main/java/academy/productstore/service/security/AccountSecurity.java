package academy.productstore.service.security;

import academy.productstore.persistence.entity.Account;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AccountSecurity {

    public boolean hasUserId(Authentication authentication, Long userId) {
        return ((Account)authentication.getPrincipal()).getId() == userId;
    }
}