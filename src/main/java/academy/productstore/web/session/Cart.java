package academy.productstore.web.session;

import academy.productstore.persistence.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SessionScope
@Component
public class Cart {

    private final Map<Product, Integer> items = new HashMap<>();

    public Map<Product, Integer> getItems() {
        return items;
    }

    public BigDecimal getAmount() {
        return items.entrySet().stream()
                .map(e -> e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Cart addItem(Product product) {
        if (this.getItems().containsKey(product)) {
            int qty = this.getItems().get(product);
            this.getItems().replace(product, qty + 1);
        } else {
            this.getItems().put(product, 1);
        }
        return this;
    }

    public Cart removeItem(Product product) {
        if (this.getItems().containsKey(product)) {
            int qty = this.getItems().get(product);
            this.getItems().replace(product, qty - 1);
            if (this.getItems().get(product) <= 0) {
                this.getItems().remove(product);
            }
        }
        return this;
    }

    public Cart removeAll() {
        this.getItems().clear();
        return this;
    }
}