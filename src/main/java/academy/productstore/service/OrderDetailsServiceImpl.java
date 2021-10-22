package academy.productstore.service;

import academy.productstore.entity.OrderDetails;
import academy.productstore.entity.Product;
import academy.productstore.repository.OrderDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public List<OrderDetails> getOrderDetailsByOrderId(long orderId) {
        return orderDetailsRepository.findOrderDetailsByOrderId(orderId);
    }

    @Override
    public void addOrderDetails(long orderId, Map<Product, Integer> items) {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            var orderDetails = new OrderDetails();
            orderDetails.setOrderId(orderId);
            orderDetails.setProductId(entry.getKey().getId());
            orderDetails.setQuantity(entry.getValue());
            orderDetails.setPrice(entry.getKey().getPrice());
            orderDetailsRepository.save(orderDetails);
        }
    }
}