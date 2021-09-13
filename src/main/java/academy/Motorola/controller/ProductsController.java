package academy.Motorola.controller;

import academy.Motorola.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products/")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        return "list";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "view";
    }
}