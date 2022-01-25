package academy.productstore.service;

import academy.productstore.domain.Account;
import academy.productstore.dto.CreateAccountDTO;

public interface AccountService {

    Account getAccount(long id);
    Account createAccount(CreateAccountDTO accountDTO);
}
