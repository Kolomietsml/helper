package academy.productstore.web.controllers;

import academy.productstore.persistence.entity.Order;
import academy.productstore.service.Cart;
import academy.productstore.service.OrderService;
import academy.productstore.web.assemblers.OrderAssembler;
import academy.productstore.web.dto.CreateOrderDTO;
import academy.productstore.web.dto.OrderDTO;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public PagedModel<OrderDTO> getOrdersByAccount(@PathVariable("user_id") long id,
                                                   Pageable pageable,
                                                   PagedResourcesAssembler<Order> pagedResourcesAssembler) {
        Page<Order> orders = orderService.getAllOrdersByAccountId(id, pageable);
        return pagedResourcesAssembler.toModel(orders, assembler);
    }

    @GetMapping("/{user_id}/orders/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") long id) {
        var order = orderService.getOrderById(id);
        return ResponseEntity.ok(assembler.toModel(order));
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