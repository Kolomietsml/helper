package academy.Motorola.controllers.admin;

import academy.Motorola.entity.Category;
import academy.Motorola.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/admin/categories")
public class AdminCategoriesRestController {

    private final CategoryService categoryService;

    public AdminCategoriesRestController(CategoryService categoryService) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}