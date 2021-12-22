package academy.productstore.service;

import academy.productstore.persistence.entity.Order;
import academy.productstore.persistence.entity.Product;
import academy.productstore.persistence.entity.Status;
import academy.productstore.web.dto.CreateOrderDTO;
import com.google.zxing.WriterException;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService {

    List<Order> getAllOrdersByAccountId(long id);
    Order getOrderById(long id);

    CreateOrderDTO checkout(Map<Product, Integer> items, BigDecimal sum, long id);

    Order addOrder(CreateOrderDTO dto, long id);
    Order updateOrder(long id, Status status);

    BufferedImage generateQRCode(long id) throws WriterException;
}