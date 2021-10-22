package academy.productstore.service;

import academy.productstore.entity.OrderDetails;
import academy.productstore.entity.Product;

import java.util.List;
import java.util.Map;

public interface OrderDetailsService {

    List<OrderDetails> getOrderDetailsByOrderId(long orderId);

    void addOrderDetails(long orderId, Map<Product, Integer> items);
}