package academy.productstore.service;

import academy.productstore.domain.Order;
import academy.productstore.domain.Status;
import academy.productstore.service.cart.Cart;
import com.google.zxing.WriterException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.awt.image.BufferedImage;

public interface OrderService {

    Page<Order> getAllOrdersByAccountId(long id, Pageable pageable);
    Order getOrderById(long id);

    Order addOrder(Cart cart, long id);
    Order updateOrder(long id, Status status);

    BufferedImage generateQRCode(long id) throws WriterException;
}