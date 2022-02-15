package academy.productstore.service;

import academy.productstore.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<Product> getAll();
    Page<Product> getProductsByCategory(long id, Pageable pageable);
    Page<Product> search(String keyword, Pageable pageable);
    Product getProductById(long id);

    Product addProduct(Product product);
    Product updateProduct(Product product, long id);
    void deleteProductById(long id);

    void deleteAll();

    Product addProductToCategory(long productId, long categoryId);
    Product removeProductFromCategory(long productId, long categoryId);
}
