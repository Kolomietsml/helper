package academy.productstore.service;

import academy.productstore.persistence.entity.*;
import academy.productstore.persistence.repository.OrderRepository;
import academy.productstore.web.dto.CreateOrderDTO;
import academy.productstore.web.dto.ItemDTO;
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
    private final AccountService accountService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            AccountService accountService) {
        this.orderRepository = orderRepository;
        this.accountService = accountService;
    }

    @Override
    public List<Order> getAllOrdersByAccountId(long id) {
        return orderRepository.findAllByAccount_Id(id);
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
    public CreateOrderDTO checkout(Map<Product, Integer> items, BigDecimal sum, long id) {
        var account = accountService.getAccount(id);
        return CreateOrderDTO.builder()
                .items(getItemsDTO(items))
                .amount(sum)
                .firstname(account.getFirstname())
                .lastname(account.getLastname())
                .phone(account.getPhone())
                .build();
    }

    @Override
    public Order addOrder(CreateOrderDTO dto, long id) {
        var account = accountService.getAccount(id);
        var order = new Order();
        order.setAmount(dto.getAmount());
        order.setItems(getItems(dto.getItems()));
        order.setDeliveryDetails(createDeliveryDetails(dto));
        order.setAccount(account);
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

    private Set<OrderItem> getItems(List<ItemDTO> items) {
        return items.stream().map(this::getItem).collect(Collectors.toSet());
    }

    private OrderItem getItem(ItemDTO dto) {
        var orderDetails = new OrderItem();
        orderDetails.setTitle(dto.getName());
        orderDetails.setQuantity(dto.getQuantity());
        orderDetails.setPrice(dto.getPrice());
        return orderDetails;
    }

    private List<ItemDTO> getItemsDTO(Map<Product, Integer> items) {
        return items.entrySet().stream().map(this::getItemDTO).collect(Collectors.toList());
    }

    private ItemDTO getItemDTO(Map.Entry<Product, Integer> entry) {
        return ItemDTO.builder()
                .name(entry.getKey().getName())
                .price(entry.getKey().getPrice())
                .quantity(entry.getValue())
                .build();
    }

    private DeliveryDetails createDeliveryDetails(CreateOrderDTO dto) {
        var details = new DeliveryDetails();
        details.setFirstName(dto.getFirstname());
        details.setLastname(dto.getLastname());
        details.setPhone(dto.getPhone());
        details.setStreet(dto.getStreet());
        details.setBuild(dto.getBuild());
        details.setApartment(dto.getApartment());
        return details;
    }
}