package academy.Motorola.controller.admin;

import academy.Motorola.entity.Product;
import academy.Motorola.service.CategoryService;
import academy.Motorola.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/products/")
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public AdminProductController(ProductService productService,
                                  CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        return "admin/products/list";
    }


    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("product", new Product());
        getCategories(model);
        return "admin/products/add";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            getCategories(model);
            return "admin/products/add";
        }
        productService.addProduct(product);
        return "redirect:/admin/products/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm (@PathVariable long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        getCategories(model);
        return "admin/products/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@Valid @ModelAttribute("product") Product product,
                                BindingResult result, @PathVariable long id, Model model){
        if (result.hasErrors()) {
            getCategories(model);
            return "admin/products/edit";
        }
        productService.updateProduct(product, id);
        return "redirect:/admin/products/";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/products/";
    }

    private void getCategories(Model model) {
        model.addAttribute("categories", categoryService.getAll());
    }
}