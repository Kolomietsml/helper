package academy.Motorola.repositories;

import academy.Motorola.entities.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductDao {

    private List<Product> products = Arrays.asList(
            new Product("Mydło", "Pieniące", new BigDecimal("25.00")),
            new Product("Masło", "Się roztrzasło", new BigDecimal("99.99")),
            new Product("Chleb", "Razowy", new BigDecimal("5.50")));

    public List<Product> all() {
        return products;
    }

    public Product byName(String name) {
        for (Product product : products) {
            if (name.equals(product.getName())) {
                return product;
            }
        }
        return null;
    }
}
