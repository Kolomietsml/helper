package academy.productstore.repository;

import academy.productstore.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"category"}, type= EntityGraph.EntityGraphType.FETCH)
    Product findProductById(long id);

    @EntityGraph(attributePaths = {"category"}, type= EntityGraph.EntityGraphType.FETCH)
    Page<Product> findProductsByCategoryId(long id, Pageable pageable);

    @EntityGraph(attributePaths = {"category"})
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
    Page<Product> search(@Param("keyword") String keyword, Pageable pageable);
}