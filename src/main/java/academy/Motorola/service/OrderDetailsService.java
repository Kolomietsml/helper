package academy.Motorola.service;

import academy.Motorola.entity.OrderDetails;
import academy.Motorola.entity.Product;

import java.util.List;
import java.util.Map;

public interface OrderDetailsService {

    List<OrderDetails> getOrderDetailsByOrderId(long orderId);

    void addOrderDetails(long orderId, Map<Product, Integer> items);
}