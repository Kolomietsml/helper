package academy.productstore.service;

import academy.productstore.persistence.entity.Order;
import academy.productstore.persistence.entity.OrderDetails;
import academy.productstore.persistence.entity.Product;
import academy.productstore.persistence.entity.Status;
import academy.productstore.persistence.repository.OrderRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(long id) {
        var order = orderRepository.findOrderById(id);
        if (order == null) {
            throw new EntityNotFoundException("Order not found");
        }
        return order;
    }

    @Override
    public Order addOrder(Map<Product, Integer> items, BigDecimal sum) {
        var order = new Order();
        order.setStatus(Status.OPEN);
        order.setAmount(sum);
        order.setDetails(getDetails(items));
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(long id, Status status) {
        var order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public BufferedImage generateQRCode(long id) throws WriterException {
        var order = getOrderById(id);
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(order.getCode().toString(), BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    private Set<OrderDetails> getDetails(Map<Product, Integer> items) {
        return items.entrySet().stream().map(this::getDetail).collect(Collectors.toSet());
    }

    private OrderDetails getDetail(Map.Entry<Product, Integer> entry) {
        var orderDetails = new OrderDetails();
        orderDetails.setTitle(entry.getKey().getName());
        orderDetails.setQuantity(entry.getValue());
        orderDetails.setPrice(entry.getKey().getPrice());
        return orderDetails;
    }
}