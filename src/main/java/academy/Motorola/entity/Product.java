package academy.Motorola.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@EqualsAndHashCode
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Field is mandatory")
    @Size(min = 3, max = 30, message = "Length must be greater than 3 and less 30")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Must be greater than 0")
    @Digits(integer=3, fraction=2)
    private BigDecimal price;

    @Column(name = "category_id")
    @NotNull
    private long categoryId;

    public Product() {
    }

    public Product(String name, String description, BigDecimal price,long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }
}