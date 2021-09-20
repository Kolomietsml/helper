package academy.Motorola.service;

import academy.Motorola.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SessionScope
@Component
public class Cart {

    private Map<Product, Integer> items = new HashMap<>();

    public Cart() {
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            sum = sum.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return sum;
    }
}