package academy.productstore.web.controllers;

import academy.productstore.web.assemblers.ProductAssembler;
import academy.productstore.web.dto.response.ProductDTO;
import academy.productstore.persistence.entity.Product;
import academy.productstore.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductsController {

    private final ProductService productService;
    private final ProductAssembler assembler;

    public ProductsController(ProductService productService,
                              ProductAssembler assembler) {
        this.productService = productService;
        this.assembler = assembler;
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