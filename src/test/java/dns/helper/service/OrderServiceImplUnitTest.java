package dns.helper.service;

import dns.helper.api.dto.request.OrderRequest;
import dns.helper.db.domain.Order;
import dns.helper.db.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplUnitTest {

    @Mock
    private OrderRepository mockRepository;

    @InjectMocks
    private OrderServiceImpl service;

    @Test
    void getAll_shouldReturnsEmptyList() {
        // given
        given(mockRepository.findAll()).willReturn(new ArrayList<>());

        // when
        var actual = service.getAll();

        // then
        assertEquals(0, actual.size());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    void getAll_shouldReturnsOrderList() {
        // given
        var orders = List.of(
                createTestOrder(1, "Article 1",
                        "https://www.gov.pl/web/udsc/informacja-w-sprawie-pobytu-w-polsce-osob-uciekajacych-z-ukrainy"),
                createTestOrder(2,
                        "Article 2", "https://www.gov.pl/web/ua"));

        given(mockRepository.findAll()).willReturn(orders);

        // when
        var actual = service.getAll();

        // then
        assertEquals(2, actual.size());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    void getOrderById_shouldThrowsEntityNotFoundException() {
        // given

        // when
        var exception =
                assertThrows(EntityNotFoundException.class, () -> service.getOrderId(anyLong()));

        // then
        assertEquals("Order not found", exception.getMessage());
        verify(mockRepository, times(1)).findOrderById(anyLong());
    }

    @Test()
    void getOrderById_shouldReturnsOder() {
        // given
        var order = createTestOrder(1, "Article 1", "https://www.gov.pl/web/ua");
        given(mockRepository.findOrderById(order.getId())).willReturn(order);

        // when
        var actual = service.getOrderId(order.getId());

        // then
        assertEquals(1, actual.getId());
        assertEquals("Article 1", actual.getContent());
        assertEquals("https://www.gov.pl/web/ua", actual.getUrl());
        verify(mockRepository, times(1)).findOrderById(order.getId());
    }

    @Test
    void addOrder_shouldReturnsOrder() {
        // given
        var order = createTestOrder(1, "Article 1", "https://www.gov.pl/web/ua");
        var orderRequest = createTestOrderRequest("Article 1", "https://www.gov.pl/web/ua");

        given(mockRepository.save(any(Order.class))).willReturn(order);

        // when
        var actual = service.addOrder(orderRequest);

        // then
        assertEquals(1, actual.getId());
        assertEquals("Article 1", actual.getContent());
        assertEquals("https://www.gov.pl/web/ua", actual.getUrl());
        verify(mockRepository, times(1)).save(any(Order.class));
    }

    @Test
    void updateOrder_shouldReturnsOrder() {
        // given
        var order = createTestOrder(1,
                "Article 2",
                "https://www.gov.pl/web/udsc/informacja-w-sprawie-pobytu-w-polsce-osob-uciekajacych-z-ukrainy");
        var orderRequest = createTestOrderRequest("Article 2",
                "https://www.gov.pl/web/udsc/informacja-w-sprawie-pobytu-w-polsce-osob-uciekajacych-z-ukrainy");
        given((mockRepository.findOrderById(order.getId()))).willReturn(order);
        given(mockRepository.save(any(Order.class))).willReturn(order);

        // when
        var actual = service.updateOrderById(orderRequest, order.getId());

        // then
        assertEquals(1, actual.getId());
        assertEquals("Article 2", actual.getContent());
        assertEquals("https://www.gov.pl/web/udsc/informacja-w-sprawie-pobytu-w-polsce-osob-uciekajacych-z-ukrainy",
                actual.getUrl());
        verify(mockRepository, times(1)).findOrderById(order.getId());
        verify(mockRepository, times(1)).save(any(Order.class));
    }

    @Test
    void updateEmergency_shouldThrowsEntityNotFoundException() {
        // given
        var orderRequest = createTestOrderRequest("Article 1", "https://www.gov.pl/web/ua");

        // when
        var exception =
                assertThrows(EntityNotFoundException.class, () -> service.updateOrderById(orderRequest, 2));

        // then
        assertEquals("Order not found", exception.getMessage());
        verify(mockRepository, times(1)).findOrderById(2);
        verify(mockRepository, times(0)).save(any(Order.class));
    }

    @Test
    void deleteOrder() {
        // given
        var order = createTestOrder(1, "Article 1", "https://www.gov.pl/web/ua");
        given(mockRepository.findOrderById(order.getId())).willReturn(order);

        // when
        service.deleteOrderById(order.getId());

        // then
        verify(mockRepository, times(1)).findOrderById(order.getId());
        verify(mockRepository, times(1)).delete(order);
    }

    @Test
    void deleteOrder_shouldThrowsEntityNotFoundException() {
        // given

        // when
        var exception =
                assertThrows(EntityNotFoundException.class, () -> service.deleteOrderById(1));

        // then
        assertEquals("Order not found", exception.getMessage());
        verify(mockRepository, times(1)).findOrderById(1);
        verify(mockRepository, times(0)).delete(any(Order.class));
    }

    private Order createTestOrder(long id, String content, String url) {
        var order = new Order();
        order.setId(id);
        order.setContent(content);
        order.setUrl(url);
        return order;
    }

    private OrderRequest createTestOrderRequest(String content, String url) {
        var orderRequest = new OrderRequest();
        orderRequest.setContent(content);
        orderRequest.setUrl(url);
        return orderRequest;
    }
}