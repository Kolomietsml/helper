package academy.productstore.service.cart;

import academy.productstore.persistence.entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SessionScope
@Component
public class Cart implements Serializable {

    @Getter
    private final Map<Product, Integer> items = new HashMap<>();

    @Getter
    @Setter
    private BigDecimal amount = BigDecimal.ZERO;
}