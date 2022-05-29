package dns.helper.db.repository;

import dns.helper.db.domain.Link;
import dns.helper.telegramBot.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("SELECT u FROM Link u WHERE u.id = :id")
    Link findLinkById(@Param("id") long id);

    @Query("SELECT l FROM Link l")
    List<Link> findAllLinks();

    @Query("SELECT l FROM Link l WHERE l.command = :command")
    List<Link> findAllLinksByCommand(@Param("command") Command command);
}
