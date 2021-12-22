package academy.productstore.persistence.repository;

import academy.productstore.persistence.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"items, deliveryDetails"}, type= EntityGraph.EntityGraphType.FETCH)
    Order findOrderById(long id);

    List<Order> findAllByAccount_Id(long id);
}