package academy.productstore.controllers.admin;

import academy.productstore.entity.Product;
import academy.productstore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductsController {

    private final ProductService productService;

    public AdminProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping()
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        var p = productService.addProduct(product);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product,
                                                 @PathVariable long id) {
        var p = productService.updateProduct(product, id);
        return ResponseEntity.ok(p);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProductById(id);
    }
}