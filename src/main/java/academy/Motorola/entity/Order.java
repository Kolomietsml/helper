package academy.Motorola.entity;

import academy.Motorola.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "date")
    private String date = setDate();

    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "status")
    private Status status;

    public Order() {
    }

    private String setDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}