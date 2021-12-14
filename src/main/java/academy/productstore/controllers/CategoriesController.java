package academy.productstore.controllers;

import academy.productstore.assemblers.CategoryAssembler;
import academy.productstore.dto.CategoryDTO;
import academy.productstore.service.CategoryService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class CategoriesController {

    private final CategoryService categoryService;
    private final CategoryAssembler assembler;

    public CategoriesController(CategoryService categoryService,
                                CategoryAssembler assembler) {
        this.categoryService = categoryService;
        this.assembler = assembler;
    }

    @GetMapping("/categories")
    public CollectionModel<CategoryDTO> getCategories() {
        var categories = categoryService.getAll();
        return assembler.toCollectionModel(categories);
    }
}