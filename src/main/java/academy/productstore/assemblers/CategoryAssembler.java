package academy.productstore.assemblers;

import academy.productstore.api.CategoryResource;
import academy.productstore.api.ProductResource;
import academy.productstore.domain.Category;
import academy.productstore.dto.response.CategoryResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryAssembler implements RepresentationModelAssembler<Category, CategoryResponse> {

    @Override
    public CategoryResponse toModel(Category category) {
        var categoryResponse = CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();

        categoryResponse.add(linkTo(ProductResource.class)
                .slash("categories")
                .slash(category.getId())
                .slash("products")
                .withSelfRel()
                .withType("GET"));

        return categoryResponse;
    }

    @Override
    public CollectionModel<CategoryResponse> toCollectionModel(Iterable<? extends Category> categories) {
        return RepresentationModelAssembler.super.toCollectionModel(categories)
                .add(linkTo(methodOn(CategoryResource.class).getCategories()).withSelfRel().withType("GET"));
    }
}