package dns.helper.db.repository;

import dns.helper.db.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("SELECT u FROM Link u WHERE u.id = :id")
    Link findLinkById(@Param("id") long id);
}
