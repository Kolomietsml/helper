package academy.productstore.assemblers;

import academy.productstore.controllers.CategoriesController;
import academy.productstore.controllers.ProductsController;
import academy.productstore.dto.CategoryDTO;
import academy.productstore.entity.Category;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryAssembler implements RepresentationModelAssembler<Category, CategoryDTO> {

    @Override
    public CategoryDTO toModel(Category category) {
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

    @Override
    public CollectionModel<CategoryDTO> toCollectionModel(Iterable<? extends Category> categories) {
        return RepresentationModelAssembler.super.toCollectionModel(categories)
                .add(linkTo(methodOn(CategoriesController.class).getCategories()).withSelfRel().withType("GET"));
    }
}