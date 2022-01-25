package academy.productstore.repository;

import academy.productstore.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Account findAccountById(@Param("id") long id);

    Account findByPhone(String phoneNumber);
}