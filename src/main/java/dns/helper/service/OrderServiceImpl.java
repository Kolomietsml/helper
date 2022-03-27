package dns.helper.service;

import dns.helper.db.domain.Order;
import dns.helper.api.dto.request.OrderRequest;
import dns.helper.db.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Override
    public List<Order> getAll() {
        return repository.findAllOrders();
    }

    @Override
    public Order getOrderId(long id) {
        var order = repository.findOrderById(id);
        if (order == null) {
            throw new EntityNotFoundException("Order not found");
        }
        return order;
    }

    @Override
    public Order addOrder(OrderRequest request) {
        var order = new Order();
        order.setDate(setDate());
        order.setContent(request.getContent());
        order.setUrl(request.getUrl());
        return repository.save(order);
    }

    @Override
    public Order updateOrderById(OrderRequest request, long id) {
        var order = getOrderId(id);
        order.setDate(setDate());
        order.setContent(request.getContent());
        order.setUrl(request.getUrl());
        return repository.save(order);
    }

    @Override
    public void deleteOrderById(long id) {
        var order = getOrderId(id);
        repository.delete(order);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    private String setDate() {
        var formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var date = new Date();
        return formatter.format(date);
    }
}