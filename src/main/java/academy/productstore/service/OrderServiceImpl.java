package academy.productstore.service;

import academy.productstore.domain.Order;
import academy.productstore.domain.Status;
import academy.productstore.dto.request.OrderRequest;
import academy.productstore.repository.OrderRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Page<Order> getAllOrdersByAccountId(long id, Pageable pageable) {
        return orderRepository.findAllByAccountId(id, pageable);
    }

    @Override
    public Order getOrderById(long id) {
        var order = orderRepository.findOrderById(id);
        if (order == null) throw new EntityNotFoundException("Order not found");
        return order;
    }

    @Override
    public Order addOrder(OrderRequest dto, long id) {
        var order = new Order();
        order.setCode(UUID.randomUUID().toString());
        order.setOrderingDate(setDate());
        //order.setAmount(cart.getAmount());
        order.setStatus(Status.OPEN);
        //order.setItems(cart.getItems().entrySet().stream().map(this::createOrderItem).collect(Collectors.toSet()));
        order.setAccountId(id);
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
        BitMatrix bitMatrix = barcodeWriter.encode(order.getCode(), BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

//    private OrderItem createOrderItem(Map.Entry<Product, Integer> entry) {
//        var orderDetails = new OrderItem();
//        orderDetails.setTitle(entry.getKey().getName());
//        orderDetails.setQuantity(entry.getValue());
//        orderDetails.setPrice(entry.getKey().getPrice());
//        return orderDetails;
//    }

    private String setDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}