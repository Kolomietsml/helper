package academy.productstore.service;

import academy.productstore.domain.Account;
import academy.productstore.dto.request.RegistrationRequest;

public interface AccountService {

    Account getAccount(long id);
    Account createAccount(RegistrationRequest registrationRequest);
}
