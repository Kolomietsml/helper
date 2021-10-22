package academy.productstore.service;

import academy.productstore.entity.Product;
import academy.productstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(long id) {
        return productRepository.findProductsByCategoryId(id);
    }

    @Override
    public Product getProductById(long id) {
        var product = productRepository.findProductById(id);
        if (product == null) {
            throw new EntityNotFoundException("Product not found");
        }
        return product;
    }

    @Override
    public Product addProduct(Product product) {
        var p = new Product();
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setCategoryId(product.getCategoryId());
        return productRepository.save(p);
    }

    @Override
    public Product updateProduct(Product product, long id) {
        var p = getProductById(id);
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setCategoryId(product.getCategoryId());
        return productRepository.save(p);
    }

    @Override
    public void deleteProductById(long id) {
        var product = getProductById(id);
        productRepository.delete(product);
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }
}