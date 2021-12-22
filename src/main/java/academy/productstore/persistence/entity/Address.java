package academy.productstore.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "street", nullable = false)
    @NotBlank(message = "Field is mandatory")
    private String street;

    @Column(name = "build", nullable = false)
    @NotBlank(message = "Field is mandatory")
    private String build;

    @Column(name = "apartment", nullable = false)
    @NotBlank(message = "Field is mandatory")
    private String apartment;
}