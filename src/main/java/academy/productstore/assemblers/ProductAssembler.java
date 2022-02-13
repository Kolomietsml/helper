package academy.productstore.assemblers;

import academy.productstore.api.ProductResource;
import academy.productstore.domain.Product;
import academy.productstore.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class ProductAssembler implements RepresentationModelAssembler<Product, ProductResponse> {

    private final CategoryAssembler categoryAssembler;

    @Override
    public ProductResponse toModel(Product product) {
        var productResponse =  ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(categoryAssembler.toModel(product.getCategory()))
                .build();

        productResponse.add(linkTo(methodOn(ProductResource.class)
                .getProduct(product.getId()))
                .withSelfRel()
                .withType("GET"));

        return productResponse;
    }
}