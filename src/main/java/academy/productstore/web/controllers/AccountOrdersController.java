package academy.productstore.web.controllers;

import academy.productstore.persistence.entity.Account;
import academy.productstore.service.Cart;
import academy.productstore.service.OrderService;
import academy.productstore.web.assemblers.OrderAssembler;
import academy.productstore.web.dto.CreateOrderDTO;
import academy.productstore.web.dto.OrderDTO;
import lombok.SneakyThrows;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/users")
public class AccountOrdersController {

    private final Cart cart;
    private final OrderService orderService;
    private final OrderAssembler assembler;

    public AccountOrdersController(Cart cart,
                                   OrderService orderService,
                                   OrderAssembler assembler) {
        this.cart = cart;
        this.orderService = orderService;
        this.assembler = assembler;
    }

    @GetMapping("/{user_id}/orders")
    public CollectionModel<OrderDTO> getOrdersByAccount(@PathVariable("user_id") long id) {
        return assembler.toCollectionModel(orderService.getAllOrdersByAccountId(id));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable long id) {
        var order = orderService.getOrderById(id);
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @GetMapping("/checkout")
    public ResponseEntity<CreateOrderDTO> orderConfirmation(@AuthenticationPrincipal Account account) {
        var dto = orderService.checkout(cart.getItems(), cart.getTotal(), account.getId());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody CreateOrderDTO dto,
                                             @PathVariable long id) {
        var order = orderService.addOrder(dto, id);
        cart.getItems().clear();
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @SneakyThrows
    @GetMapping(value = "/orders/{id}/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCode(@PathVariable long id) {
        BufferedImage image = orderService.generateQRCode(id);
        return ResponseEntity.ok(image);
    }
}