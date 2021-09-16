package academy.Motorola.controller;

import academy.Motorola.bean.Cart;
import academy.Motorola.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final Cart cart;
    private final OrderService orderService;

    public OrdersController(Cart cart, OrderService orderService) {
        this.cart = cart;
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public String addOrder() {
        orderService.addOrder(cart.getItems(), cart.getTotal());
        cart.getItems().clear();
        return "redirect:/products/";
    }
}