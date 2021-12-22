package academy.productstore.service;

import academy.productstore.persistence.entity.Account;
import academy.productstore.persistence.repository.AccountRepository;
import academy.productstore.web.dto.CreateAccountDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountServiceImpl(AccountRepository repository,
                              BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Account getAccount(long id) {
        var account = repository.findAccountById(id);
        if (account == null) {
            throw new EntityNotFoundException("Account not found");
        }
        return account;
    }

    @Override
    public Account createAccount(CreateAccountDTO accountDTO) {
        var account = new Account();
        account.setFirstname(accountDTO.getFirstname());
        account.setLastname(accountDTO.getLastname());
        account.setEmail(accountDTO.getEmail());
        account.setPhone(accountDTO.getPhone());
        account.setPassword(bCryptPasswordEncoder.encode(accountDTO.getPassword()));
        account.setEnabled(true);
        return repository.save(account);
    }
}