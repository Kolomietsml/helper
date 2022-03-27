package dns.helper.db.repository;

import dns.helper.db.domain.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmergencyRepository extends JpaRepository<Emergency, Long> {

    @Query("SELECT e FROM Emergency e WHERE e.id = :id")
    Emergency findEmergencyById(@Param("id") long id);

    @Query("SELECT e FROM Emergency e")
    List<Emergency> findAllEmergencies();
}