package academy.productstore.service.cart;

import academy.productstore.persistence.entity.Product;
import academy.productstore.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    private final Cart cart;
    private final ProductService productService;

    public CartServiceImpl(Cart cart, ProductService productService) {
        this.cart = cart;
        this.productService = productService;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public Cart addItem(long id) {
        var product = productService.getProductById(id);
        if (cart.getItems().containsKey(product)) {
            int qty = cart.getItems().get(product);
            cart.getItems().replace(product, qty + 1);
        } else {
            cart.getItems().put(product, 1);
        }
        BigDecimal amount = getAmount(cart.getItems());
        cart.setAmount(amount);
        return cart;
    }

    @Override
    public Cart removeItem(long id) {
        var product = productService.getProductById(id);
        if (cart.getItems().containsKey(product)) {
            int qty = cart.getItems().get(product);
            cart.getItems().replace(product, qty - 1);
            if (cart.getItems().get(product) <= 0) {
                cart.getItems().remove(product);
            }
        }
        BigDecimal amount = getAmount(cart.getItems());
        cart.setAmount(amount);
        return cart;
    }

    @Override
    public Cart removeAllItems() {
        cart.getItems().clear();
        cart.setAmount(BigDecimal.ZERO);
        return cart;
    }

    private BigDecimal getAmount(Map<Product, Integer> items) {
        return items.entrySet().stream()
                .map(e -> e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}