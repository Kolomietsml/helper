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
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Field is mandatory")
    @Size(min = 3, max = 30)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal price;

    @Column(name = "category_id")
    @NotNull
    private long categoryId;

    public Product() {
    }
}