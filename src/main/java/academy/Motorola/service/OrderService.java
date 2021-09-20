package academy.Motorola.service;

import academy.Motorola.entity.Order;
import academy.Motorola.entity.Product;
import academy.Motorola.enums.Status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService {

    List<Order> getAll();
    Order getOrderById(long id);

    Order addOrder(Map<Product, Integer> items, BigDecimal sum);
    Order updateOrder(long id, Status status);
}