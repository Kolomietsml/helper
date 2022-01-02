package academy.productstore.web.controllers;

import academy.productstore.service.CategoryService;
import academy.productstore.web.assemblers.CategoryAssembler;
import academy.productstore.web.dto.CategoryDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class CategoriesController {

    private final CategoryService categoryService;
    private final CategoryAssembler assembler;

    public CategoriesController(CategoryService categoryService,
                                CategoryAssembler assembler) {
        this.categoryService = categoryService;
        this.assembler = assembler;
    }

    @GetMapping()
    public CollectionModel<CategoryDTO> getCategories() {
        var categories = categoryService.getAll();
        return assembler.toCollectionModel(categories);
    }
}