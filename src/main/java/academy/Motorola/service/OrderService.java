package academy.Motorola.service;

import academy.Motorola.entity.Order;
import academy.Motorola.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService {

    List<Order> getAll();

    void addOrder(Map<Product, Integer> items, BigDecimal sum);
}