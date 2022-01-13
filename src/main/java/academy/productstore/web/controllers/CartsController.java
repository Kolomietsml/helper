package academy.productstore.web.controllers;

import academy.productstore.service.cart.CartService;
import academy.productstore.web.assemblers.CartAssembler;
import academy.productstore.web.dto.CartDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/cart")
public class CartsController {

    private final CartService service;
    private final CartAssembler assembler;

    public CartsController(CartService service, CartAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping()
    public ResponseEntity<CartDTO> showCart() {
        return ResponseEntity.ok(assembler.toModel(service.getCart()));
    }

    @PostMapping()
    public ResponseEntity<CartDTO> addProductToCart(@RequestBody HashMap<String, Long> request) {
        return ResponseEntity.ok(assembler.toModel(service.addItem(request.get("id"))));
    }

    @PutMapping()
    public ResponseEntity<CartDTO> removeProductFromCart(@RequestBody HashMap<String, Long> request) {
        return ResponseEntity.ok(assembler.toModel(service.removeItem(request.get("id"))));
    }

    @DeleteMapping()
    public ResponseEntity<CartDTO> removeAllProductsFromCart() {
        return ResponseEntity.ok(assembler.toModel(service.removeAllItems()));
    }
}