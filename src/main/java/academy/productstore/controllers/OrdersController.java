package academy.productstore.controllers;

import academy.productstore.domain.Account;
import academy.productstore.domain.Order;
import academy.productstore.service.OrderService;
import academy.productstore.service.cart.CartService;
import academy.productstore.assemblers.OrderAssembler;
import academy.productstore.dto.OrderDTO;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/users")
public class OrdersController {

    private final OrderService orderService;
    private final OrderAssembler assembler;
    private final CartService cartService;

    public OrdersController(OrderService orderService,
                            OrderAssembler assembler,
                            CartService cartService) {
        this.orderService = orderService;
        this.assembler = assembler;
        this.cartService = cartService;
    }

    @GetMapping("/{user_id}/orders")
    public PagedModel<OrderDTO> getOrdersByAccount(@PathVariable("user_id") long userId,
                                                   Pageable pageable,
                                                   PagedResourcesAssembler<Order> pagedResourcesAssembler) {
        Page<Order> orders = orderService.getAllOrdersByAccountId(userId, pageable);
        return pagedResourcesAssembler.toModel(orders, assembler);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable long id) {
        var order = orderService.getOrderById(id);
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @PostMapping("/{user_id}/orders")
    public ResponseEntity<OrderDTO> addOrder(@PathVariable("user_id") long userId) {
        var order = orderService.addOrder(cartService.getCart(), userId);
        cartService.removeAllItems();
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @SneakyThrows
    @GetMapping(value = "/orders/{id}/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCode(@PathVariable long id) {
        BufferedImage image = orderService.generateQRCode(id);
        return ResponseEntity.ok(image);
    }

    private long getPrincipalId() {
        return ((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}