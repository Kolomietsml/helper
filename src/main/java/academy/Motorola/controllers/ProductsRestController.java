package academy.Motorola.controllers;

import academy.Motorola.entity.Product;
import academy.Motorola.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductsRestController {

    private final ProductService productService;

    public ProductsRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getProducts() {
        return productService.getAll();
    }

    @GetMapping("/category/{id}")
    public List<Product> getProductsByCategory(@PathVariable long id) {
        return productService.getProductsByCategory(id);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable long id) {
        return productService.getProductById(id);
    }
}