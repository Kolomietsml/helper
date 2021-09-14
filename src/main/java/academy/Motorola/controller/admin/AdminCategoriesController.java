package academy.Motorola.controller.admin;

import academy.Motorola.entity.Category;
import academy.Motorola.entity.Product;
import academy.Motorola.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/categories/")
public class AdminCategoriesController {

    private final CategoryService categoryService;

    public AdminCategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "admin/categories/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("category", new Category());
        return "admin/categories/add";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/categories/add";
        }
        categoryService.addCategory(category);
        return "redirect:/admin/categories/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm (@PathVariable long id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "admin/categories/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, @PathVariable long id){
        if (result.hasErrors()) {
            return "admin/categories/edit";
        }
        categoryService.updateCategory(category, id);
        return "redirect:/admin/categories/";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/admin/categories/";
    }


}
