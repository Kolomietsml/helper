package academy.productstore.api;

import academy.productstore.assemblers.CategoryAssembler;
import academy.productstore.dto.response.CategoryResponse;
import academy.productstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class CategoryResource {

    private final CategoryService categoryService;
    private final CategoryAssembler assembler;

    @GetMapping("/categories")
    public CollectionModel<CategoryResponse> getCategories() {
        var categories = categoryService.getAll();
        return assembler.toCollectionModel(categories);
    }
}