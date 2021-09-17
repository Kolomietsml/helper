package academy.Motorola.controller.admin;

import academy.Motorola.entity.Order;
import academy.Motorola.enums.Status;
import academy.Motorola.service.OrderDetailsService;
import academy.Motorola.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("statusTypes", Status.values());
        return "admin/orders/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateOrder(@ModelAttribute("order")Order order,
                              @PathVariable long id) {
        orderService.updateOrder(id, order.getStatus());
        return "redirect:/admin/orders/";
    }
}