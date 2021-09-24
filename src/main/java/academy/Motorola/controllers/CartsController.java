package academy.Motorola.controllers;

import academy.Motorola.service.Cart;
import academy.Motorola.entity.Product;
import academy.Motorola.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/cart")
public class CartsController {

    private final Cart cart;
    private final CartService cartService;

    public CartsController(Cart cart, CartService cartService) {
        this.cart = cart;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public Map<Product, Integer> showCart() {
        return cart.getItems();
    }

    @PostMapping("/add/{id}")
    public Map<Product, Integer> addProductToCart(@PathVariable long id) {
        return cartService.addProductToCart(cart, id).getItems();

    }

    @PutMapping("/remove/{id}")
    public Map<Product, Integer> removeProductFromCart(@PathVariable long id) {
        return cartService.removeProductFromCart(cart, id).getItems();
    }
}
