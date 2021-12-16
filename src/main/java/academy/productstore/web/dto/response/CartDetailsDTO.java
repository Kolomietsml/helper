package academy.productstore.web.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Builder
public class CartDetailsDTO extends RepresentationModel<CartDetailsDTO> {

    private String name;
    private BigDecimal price;
    private int quantity;
}