package academy.productstore.controllers.admin;

import academy.productstore.domain.Category;
import academy.productstore.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/admin/categories")
public class AdminCategoriesController {

    private final CategoryService categoryService;

    public AdminCategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<Category> getCategories() {
        return categoryService.getAll();
    }

    @PostMapping()
    public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category) {
        var c = categoryService.addCategory(category);
        return ResponseEntity.ok(c);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category>  updateCategory(@Valid @RequestBody Category category,
                                                    @PathVariable long id){
        var c = categoryService.updateCategory(category, id);
        return ResponseEntity.ok(c);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
    }
}