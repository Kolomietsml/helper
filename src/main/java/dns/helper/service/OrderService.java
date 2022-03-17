package dns.helper.service;

import dns.helper.db.domain.Order;
import dns.helper.api.dto.request.OrderRequest;

import java.util.List;

public interface OrderService {

    List<Order> getAll();
    Order getOrderId(long id);
    Order addOrder(OrderRequest request);
    Order updateOrderById(OrderRequest request, long id);
    void deleteOrderById(long id);
    void deleteAll();
}