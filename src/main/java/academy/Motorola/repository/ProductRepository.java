package academy.Motorola.repository;

import academy.Motorola.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findProductById(@Param("id") long id);

    @Query("SELECT p FROM Product p WHERE p.categoryId = :id")
    List<Product> findProductsByCategoryId(@Param("id") long id);
}
