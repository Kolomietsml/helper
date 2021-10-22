package academy.productstore.controllers;

import academy.productstore.service.Cart;
import academy.productstore.entity.Order;
import academy.productstore.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/orders")
public class OrdersController {

    private final Cart cart;
    private final OrderService orderService;

    public OrdersController(Cart cart, OrderService orderService) {
        this.cart = cart;
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder() {
        var order = orderService.addOrder(cart.getItems(), cart.getTotal());
        cart.getItems().clear();
        return ResponseEntity.ok(order);

    }
}
