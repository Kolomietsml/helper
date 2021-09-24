package academy.Motorola.service;

import academy.Motorola.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();
    List<Product> getProductsByCategory(long id);
    Product getProductById(long id);

    Product addProduct(Product product);
    Product updateProduct(Product product, long id);
    void deleteProductById(long id);

    void deleteAll();
}
