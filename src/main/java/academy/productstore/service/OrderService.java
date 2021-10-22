package academy.productstore.service;

import academy.productstore.entity.Order;
import academy.productstore.entity.Product;
import academy.productstore.enums.Status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService {

    List<Order> getAll();
    Order getOrderById(long id);

    Order addOrder(Map<Product, Integer> items, BigDecimal sum);
    Order updateOrder(long id, Status status);
}