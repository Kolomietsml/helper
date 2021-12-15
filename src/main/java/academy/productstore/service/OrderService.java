package academy.productstore.service;

import academy.productstore.persistence.entity.Order;
import academy.productstore.persistence.entity.Product;
import academy.productstore.persistence.entity.Status;
import com.google.zxing.WriterException;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService {

    List<Order> getAll();
    Order getOrderById(long id);

    Order addOrder(Map<Product, Integer> items, BigDecimal sum);
    Order updateOrder(long id, Status status);

    BufferedImage generateQRCode(long id) throws WriterException;
}