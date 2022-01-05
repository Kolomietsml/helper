package academy.productstore.web.assemblers;

import academy.productstore.web.controllers.CartsController;
import academy.productstore.web.controllers.ProductsController;
import academy.productstore.web.dto.CategoryDTO;
import academy.productstore.web.dto.ProductDTO;
import academy.productstore.persistence.entity.Category;
import academy.productstore.persistence.entity.Product;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductAssembler implements RepresentationModelAssembler<Product, ProductDTO> {

    @Override
    public ProductDTO toModel(Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(toCategoryDTO(product.getCategory()))
                .build()
                .add(linkTo(methodOn(ProductsController.class)
                        .getProduct(product.getId()))
                        .withSelfRel()
                        .withType("GET"))
                .add(linkTo(methodOn(CartsController.class)
                        .addProductToCart(new HashMap<>())).withRel("add_to_cart")
                        .withType("POST"));
    }

    private CategoryDTO toCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .build()
                .add(linkTo(ProductsController.class)
                        .slash("categories")
                        .slash(category.getId())
                        .slash("products")
                        .withSelfRel()
                        .withType("GET"));
    }
}
