package academy.Motorola.controllers;

import academy.Motorola.repositories.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductsController {

    private final ProductDao productDao;

    public ProductsController(ProductDao productDao) {
        this.productDao = productDao;
    }


    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productDao.all());
        return "list";
    }

    @GetMapping("/products/{name}")
    public String getProduct(@PathVariable String name, Model model) {
        model.addAttribute("product", productDao.byName(name));
        return "view";
    }
}