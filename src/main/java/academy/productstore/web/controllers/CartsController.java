package academy.productstore.web.controllers;

import academy.productstore.web.assemblers.CartAssembler;
import academy.productstore.web.dto.CartDTO;
import academy.productstore.service.Cart;
import academy.productstore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    @PostMapping()
    public ResponseEntity<CartDTO> addProductToCart(@RequestBody HashMap<String, Long> request) {
        return ResponseEntity.ok(assembler.toModel(cartService.addProductToCart(cart, request.get("id"))));
    }

    @PutMapping()
    public ResponseEntity<CartDTO> removeProductFromCart(@RequestBody HashMap<String, Long> request) {
        return ResponseEntity.ok(assembler.toModel(cartService.removeProductFromCart(cart, request.get("id"))));
    }

    @DeleteMapping()
    public ResponseEntity<CartDTO> removeAllProductsFromCart() {
        return ResponseEntity.ok(assembler.toModel(cartService.removeAll(cart)));
    }
}