package academy.productstore.service.security;

import academy.productstore.persistence.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public AccountDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        try{
            var account = accountRepository.findByPhone(phone);
            if (account == null) {
                throw new UsernameNotFoundException("No user found with the phoneNumber: " + phone);
            }
            return  account;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}