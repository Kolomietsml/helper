package academy.productstore.api;

import academy.productstore.assemblers.ProductAssembler;
import academy.productstore.domain.Product;
import academy.productstore.dto.response.ProductResponse;
import academy.productstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class ProductResource {

    private final ProductService productService;
    private final ProductAssembler assembler;

    @GetMapping("/categories/{id}/products")
    public PagedModel<ProductResponse> getProductsByCategory(@PathVariable long id,
                                                             Pageable pageable,
                                                             PagedResourcesAssembler<Product> pagedResourcesAssembler) {
        Page<Product> products = productService.getProductsByCategory(id, pageable);
        return pagedResourcesAssembler.toModel(products, assembler);
    }

    @GetMapping("/products/search")
    public PagedModel<ProductResponse> search(@RequestParam("keyword") String keyword, Pageable pageable,
                                              PagedResourcesAssembler<Product> pagedResourcesAssembler) {
        Page<Product> products = productService.search(keyword, pageable);
        return pagedResourcesAssembler.toModel(products, assembler);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable long id) {
        var product = productService.getProductById(id);
        return ResponseEntity.ok(assembler.toModel(product));
    }
}