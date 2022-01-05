package academy.productstore.service;

import academy.productstore.persistence.entity.Category;
import academy.productstore.persistence.entity.Product;
import academy.productstore.persistence.repository.CategoryRepository;
import academy.productstore.persistence.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

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

        Category category = getOrCreateCategory(product.getCategory());
        p.setCategory(category);

        return productRepository.save(p);
    }

    @Override
    public Product updateProduct(Product product, long id) {
        var p = getProductById(id);
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());

        Category category = getOrCreateCategory(product.getCategory());
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

    private Category getOrCreateCategory(Category c) {
        Category category;
        if(c.getId() != 0) {
            Optional<Category> optional = categoryRepository.findById(c.getId());
            if(optional.isPresent()){
                category = optional.get();
                return category;
            } else {
                category = new Category();
            }
        } else {
            category = new Category();
        }
        category.setName(c.getName());
        return category;
    }
}