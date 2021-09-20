package academy.Motorola.controllers.admin;

import academy.Motorola.entity.Order;
import academy.Motorola.entity.OrderDetails;
import academy.Motorola.service.OrderDetailsService;
import academy.Motorola.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/admin/orders")
public class AdminOrdersRestController {


    private final OrderService orderService;
    private final OrderDetailsService orderDetailsService;

    public AdminOrdersRestController(OrderService orderService, OrderDetailsService orderDetailsService) {
        this.orderService = orderService;
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping()
    public List<Order> getOrders() {
        return orderService.getAll();

    }

    @GetMapping("/{id}")
    public List<OrderDetails> getOrderDetails(@PathVariable long id) {
        return orderDetailsService.getOrderDetailsByOrderId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order,
                                      @PathVariable long id) {
        var o = orderService.updateOrder(id, order.getStatus());
        return ResponseEntity.ok(o);
    }
}
