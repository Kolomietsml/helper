package academy.productstore.service;

import academy.productstore.domain.Account;
import academy.productstore.repository.AccountRepository;
import academy.productstore.dto.CreateAccountDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        var account = accountRepository.findByPhone(phone);
        if (account == null) {
            log.error("Account not found");
            throw new UsernameNotFoundException("Account not found");
        } else {
            log.info("Account found in database: {}", phone);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        account.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new User(account.getPhone(), account.getPassword(), authorities);
    }

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
    public Account createAccount(CreateAccountDTO accountDTO) {
        var account = new Account();
        account.setFirstname(accountDTO.getFirstname());
        account.setLastname(accountDTO.getLastname());
        account.setEmail(accountDTO.getEmail());
        account.setPhone(accountDTO.getPhone());
        account.setPassword(bCryptPasswordEncoder.encode(accountDTO.getPassword()));
        log.info("Saving new account {} to the database", account.getPhone());
        return accountRepository.save(account);
    }
}