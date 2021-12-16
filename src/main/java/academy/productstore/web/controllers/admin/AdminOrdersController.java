package academy.productstore.web.controllers.admin;

import academy.productstore.persistence.entity.Order;
import academy.productstore.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/admin/orders")
public class AdminOrdersController {

    private final OrderService orderService;

    public AdminOrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public List<Order> getOrders() {
        return orderService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order,
                                      @PathVariable long id) {
        var o = orderService.updateOrder(id, order.getStatus());
        return ResponseEntity.ok(o);
    }
}