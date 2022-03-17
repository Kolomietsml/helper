package dns.helper.service;

import dns.helper.db.domain.Account;
import dns.helper.api.dto.request.RegistrationRequest;

public interface AccountService {

    Account getAccount(long id);
    Account createAccount(RegistrationRequest registrationRequest);
}
