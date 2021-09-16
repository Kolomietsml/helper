package academy.Motorola.controller.admin;

import academy.Motorola.service.OrderDetailsService;
import academy.Motorola.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrdersController {

    private final OrderService orderService;
    private final OrderDetailsService orderDetailsService;

    public AdminOrdersController(OrderService orderService, OrderDetailsService orderDetailsService) {
        this.orderService = orderService;
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping("/")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "admin/orders/list";
    }

    @GetMapping("/{id}")
    public String getOrderDetails(@PathVariable long id, Model model) {
        model.addAttribute("order_details", orderDetailsService.getOrderDetailsByOrderId(id));
        return "admin/orders/view";
    }
}