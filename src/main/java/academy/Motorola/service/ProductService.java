package academy.Motorola.service;

import academy.Motorola.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();
    List<Product> getProductsByCategory(long id);
    Product getProductById(long id);

    void addProduct(Product product);
    void updateProduct(Product product, long id);
    void deleteProductById(long id);
}
