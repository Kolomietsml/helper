package academy.productstore.web.controllers;

import academy.productstore.service.Cart;
import academy.productstore.service.OrderService;
import academy.productstore.web.assemblers.OrderAssembler;
import academy.productstore.web.dto.response.OrderDTO;
import lombok.SneakyThrows;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final Cart cart;
    private final OrderService orderService;
    private final OrderAssembler assembler;

    public OrdersController(Cart cart, OrderService orderService, OrderAssembler assembler) {
        this.cart = cart;
        this.orderService = orderService;
        this.assembler = assembler;
    }

    @GetMapping()
    public CollectionModel<OrderDTO> getOrders() {
        return assembler.toCollectionModel(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable long id) {
        var order = orderService.getOrderById(id);
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @PostMapping("/")
    public ResponseEntity<OrderDTO> addOrder() {
        var order = orderService.addOrder(cart.getItems(), cart.getTotal());
        cart.getItems().clear();
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @SneakyThrows
    @GetMapping(value = "/{id}/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCode(@PathVariable long id) {
        BufferedImage image = orderService.generateQRCode(id);
        return ResponseEntity.ok(image);
    }
}