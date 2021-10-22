package academy.productstore.service;

import academy.productstore.entity.Order;
import academy.productstore.entity.Product;
import academy.productstore.enums.Status;
import academy.productstore.repository.OrderRepository;
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
    public Order addOrder(Map<Product, Integer> items, BigDecimal sum) {
        var order = new Order();
        order.setStatus(Status.OPEN);
        order.setAmount(sum);
        var savedOrder = orderRepository.save(order);
        orderDetailsService.addOrderDetails(savedOrder.getId(), items);
        return savedOrder;

    }

    @Override
    public Order updateOrder(long id, Status status) {
        var order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}