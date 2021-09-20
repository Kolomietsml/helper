package academy.Motorola.controllers.admin;

import academy.Motorola.entity.Product;
import academy.Motorola.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/admin/products")
public class AdminProductsRestController {

    private final ProductService productService;

    public AdminProductsRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getProducts() {
        return productService.getAll();
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
