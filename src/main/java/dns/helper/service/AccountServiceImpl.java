package dns.helper.service;

import dns.helper.db.domain.Account;
import dns.helper.api.dto.request.RegistrationRequest;
import dns.helper.exception.AccountAlreadyExistsException;
import dns.helper.db.repository.AccountRepository;
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
    public Account createAccount(RegistrationRequest request) {
        var existingAccount = accountRepository.findByEmail(request.getEmail());
        if (existingAccount != null) {
            throw new AccountAlreadyExistsException("Account already exists");
        }
        var account = new Account();
        account.setEmail(request.getEmail());
        account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        log.info("Saving new account {} to the database", account.getEmail());
        return accountRepository.save(account);
    }
}