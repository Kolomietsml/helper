package academy.productstore.web.session;

import academy.productstore.persistence.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartUnitTest {

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
    }

    @Test
    void getItems() {
        // given
        cart.getItems().put(createTestProduct(1L, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5)), 1);
        cart.getItems().put(createTestProduct(2L, "Pepsi", "", BigDecimal.valueOf(0.45)), 1);

        // when
        int actual = cart.getItems().size();

        // then
        assertEquals(2, actual);
    }

    @Test
    void addItem() {
        // given
        var p1 = createTestProduct(1L, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5));
        var p2 = createTestProduct(2L, "Pepsi", "", BigDecimal.valueOf(0.45));
        cart.getItems().put(p1, 1);

        // when
        cart.addItem(p1);
        cart.addItem(p2);

        // then
        assertEquals(2, cart.getItems().size());
        assertEquals(2, cart.getItems().get(p1));
        assertEquals(1, cart.getItems().get(p2));
    }

    @Test
    void removeItem() {
        // given
        var p1 = createTestProduct(1L, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5));
        var p2 = createTestProduct(2L, "Pepsi", "", BigDecimal.valueOf(0.45));
        cart.getItems().put(p1, 2);
        cart.getItems().put(p2, 1);

        // when
        cart.removeItem(p1);
        cart.removeItem(p2);

        // then
        assertEquals(1, cart.getItems().size());
        assertEquals(1, cart.getItems().get(p1));
        assertNull(cart.getItems().get(p2));
    }

    @Test
    void removeAll() {
        // given
        var p1 = createTestProduct(1L, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5));
        var p2 = createTestProduct(2L, "Pepsi", "", BigDecimal.valueOf(0.45));
        cart.getItems().put(p1, 2);
        cart.getItems().put(p2, 1);

        // when
        cart.removeAll();

        // then
        assertEquals(0, cart.getItems().size());
    }

    @Test
    void getAmount() {
        // given
        var p1 = createTestProduct(1L, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5));
        var p2 = createTestProduct(2L, "Pepsi", "", BigDecimal.valueOf(0.45));
        cart.getItems().put(p1, 2);
        cart.getItems().put(p2, 1);

        // when
        BigDecimal actual = cart.getAmount();

        // then
        assertEquals(BigDecimal.valueOf(1.45), actual);
    }

    private Product createTestProduct(long id, String name, String desc, BigDecimal price) {
        var product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(desc);
        product.setPrice(price);
        return product;
    }
}