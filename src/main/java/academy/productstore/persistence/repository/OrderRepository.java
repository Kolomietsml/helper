package academy.productstore.persistence.repository;

import academy.productstore.persistence.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"details"}, type= EntityGraph.EntityGraphType.FETCH)
    Order findOrderById(long id);
}