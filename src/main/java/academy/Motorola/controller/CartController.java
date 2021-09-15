package academy.Motorola.controller;

import academy.Motorola.bean.Cart;
import academy.Motorola.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final Cart cart;
    private final CartService cartService;

    public CartController(Cart cart, CartService cartService) {
        this.cart = cart;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String showCart(Model model) {
        model.addAttribute("cart", cart);
        return "cart/view";
    }

    @PostMapping("/add/{id}")
    public String addProductToCart(@PathVariable long id, Model model) {
        model.addAttribute("cart", cartService.addProductToCart(cart, id));
        return "redirect:/cart/";
    }

    @PostMapping("/remove/{id}")
    public String removeProductFromCart(@PathVariable long id, Model model) {
        model.addAttribute("cart", cartService.removeProductFromCart(cart, id));
        return "redirect:/cart/";
    }
}