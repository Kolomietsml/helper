package academy.Motorola.service;

import academy.Motorola.entity.Order;
import academy.Motorola.entity.Product;
import academy.Motorola.enums.Status;
import academy.Motorola.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
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
    public Order getOrderById(long id) {
        var order = orderRepository.getById(id);
        if (order == null) {
            throw new EntityNotFoundException("Order not found");
        }
        return order;
    }

    @Override
    public void addOrder(Map<Product, Integer> items, BigDecimal sum) {
        if (!items.isEmpty()) {
            var order = new Order();
            order.setStatus(Status.OPEN);
            order.setAmount(sum);
            orderDetailsService.addOrderDetails(orderRepository.save(order).getId(), items);
        }
    }

    @Override
    public void updateOrder(long id, Status status) {
        var order = getOrderById(id);
        order.setStatus(status);
        orderRepository.save(order);
    }
}