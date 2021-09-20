package academy.Motorola.controllers;

import academy.Motorola.entity.Category;
import academy.Motorola.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoriesRestController {

    private final CategoryService categoryService;

    public CategoriesRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<Category> getCategories() {
        return categoryService.getAll();
    }
}
