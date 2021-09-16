package academy.Motorola.service;

import academy.Motorola.entity.Order;
import academy.Motorola.entity.Product;
import academy.Motorola.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsService orderDetailsService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderDetailsService orderDetailsService) {
        this.orderRepository = orderRepository;
        this.orderDetailsService = orderDetailsService;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void addOrder(Map<Product, Integer> items, BigDecimal sum) {
        var order = new Order();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        order.setDate(formatter.format(date));
        order.setAmount(sum);

        orderDetailsService.addOrderDetails(orderRepository.save(order).getId(), items);
    }
}