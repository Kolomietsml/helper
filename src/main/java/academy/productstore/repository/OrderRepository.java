package academy.productstore.repository;

import academy.productstore.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"items"}, type= EntityGraph.EntityGraphType.FETCH)
    Order findOrderById(long id);

    @EntityGraph(attributePaths = {"items"}, type= EntityGraph.EntityGraphType.FETCH)
    Page<Order> findAllByAccountId(long id, Pageable pageable);
}