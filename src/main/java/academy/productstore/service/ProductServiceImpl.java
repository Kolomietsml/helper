package academy.productstore.service;

import academy.productstore.domain.Category;
import academy.productstore.domain.Product;
import academy.productstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getProductsByCategory(long id, Pageable pageable) {
        return productRepository.findProductsByCategoryId(id, pageable);
    }

    @Override
    public Page<Product> search(String keyword, Pageable pageable) {
        return productRepository.search(keyword, pageable);
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

        Category category = categoryService.getCategoryById(product.getCategory().getId());
        p.setCategory(category);

        return productRepository.save(p);
    }

    @Override
    public Product updateProduct(Product product, long id) {
        var p = getProductById(id);
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());

        Category category = categoryService.getCategoryById(product.getCategory().getId());
        p.setCategory(category);

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