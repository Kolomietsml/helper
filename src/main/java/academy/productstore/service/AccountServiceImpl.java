package academy.productstore.service;

import academy.productstore.domain.Account;
import academy.productstore.dto.request.RegistrationRequest;
import academy.productstore.exception.AccountAlreadyExistsException;
import academy.productstore.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Account getAccount(long id) {
        var account = accountRepository.findAccountById(id);
        if (account == null) {
            throw new EntityNotFoundException("Account not found");
        }
        log.info("Fetching account {}", id);
        return account;
    }

    @Override
    public Account createAccount(RegistrationRequest dto) {
        var existingAccount = accountRepository.findByPhone(dto.getPhone());
        if (existingAccount != null) {
            throw new AccountAlreadyExistsException("Account already exists");
        }
        var account = new Account();
        account.setFirstname(dto.getFirstname());
        account.setLastname(dto.getLastname());
        account.setEmail(dto.getEmail());
        account.setPhone(dto.getPhone());
        account.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        log.info("Saving new account {} to the database", account.getPhone());
        return accountRepository.save(account);
    }
}