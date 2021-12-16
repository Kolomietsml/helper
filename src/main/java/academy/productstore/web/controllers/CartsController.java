package academy.productstore.web.controllers;

import academy.productstore.web.assemblers.CartAssembler;
import academy.productstore.web.dto.response.CartDTO;
import academy.productstore.service.Cart;
import academy.productstore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartsController {

    private final Cart cart;
    private final CartService cartService;
    private final CartAssembler assembler;

    public CartsController(Cart cart, CartService cartService, CartAssembler assembler) {
        this.cart = cart;
        this.cartService = cartService;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public ResponseEntity<CartDTO> showCart() {
        return ResponseEntity.ok(assembler.toModel(cart));
    }

    @PostMapping("/{id}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable long id) {
        return ResponseEntity.ok(assembler.toModel(cartService.addProductToCart(cart, id)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDTO> removeProductFromCart(@PathVariable long id) {
        return ResponseEntity.ok(assembler.toModel(cartService.removeProductFromCart(cart, id)));
    }
}