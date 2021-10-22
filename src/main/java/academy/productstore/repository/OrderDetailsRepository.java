package academy.productstore.repository;

import academy.productstore.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    @Query("SELECT o FROM OrderDetails o WHERE o.orderId = :id")
    List<OrderDetails> findOrderDetailsByOrderId(@Param("id") long id);
}