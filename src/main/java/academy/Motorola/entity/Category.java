package academy.Motorola.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Field is mandatory")
    @Size(min = 3, max = 50, message = "Length must be greater than 3 and less 50")
    private String name;

    public Category() {
    }
}