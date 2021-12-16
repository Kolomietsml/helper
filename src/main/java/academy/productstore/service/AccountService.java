package academy.productstore.service;

import academy.productstore.persistence.entity.Account;
import academy.productstore.web.dto.request.CreateAccountDTO;

public interface AccountService {

    Account getAccount(long id);

    Account createAccount(CreateAccountDTO accountDTO);
}
