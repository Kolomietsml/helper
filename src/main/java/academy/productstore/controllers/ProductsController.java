package academy.productstore.controllers;

import academy.productstore.assemblers.ProductAssembler;
import academy.productstore.dto.ProductDTO;
import academy.productstore.entity.Product;
import academy.productstore.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class ProductsController {

    private final ProductService productService;
    private final ProductAssembler assembler;

    public ProductsController(ProductService productService,
                              ProductAssembler assembler) {
        this.productService = productService;
        this.assembler = assembler;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getAll();
    }

    @GetMapping("/categories/{id}/products")
    public PagedModel<ProductDTO> getProductsByCategory(@PathVariable long id,
                                                  Pageable pageable,
                                                  PagedResourcesAssembler<Product> pagedResourcesAssembler) {
        Page<Product> products = productService.getProductsByCategory(id, pageable);
        return pagedResourcesAssembler.toModel(products, assembler);
    }

    @GetMapping("/products/search")
    public PagedModel<ProductDTO> search(@RequestParam("keyword") String keyword, Pageable pageable,
                                                        PagedResourcesAssembler<Product> pagedResourcesAssembler) {
        Page<Product> products = productService.search(keyword, pageable);
        return pagedResourcesAssembler.toModel(products, assembler);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable long id) {
        var product = productService.getProductById(id);
        return ResponseEntity.ok(assembler.toModel(product));
    }
}