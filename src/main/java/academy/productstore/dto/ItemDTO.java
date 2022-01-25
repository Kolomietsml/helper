package academy.productstore.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Builder
public class ItemDTO extends RepresentationModel<ItemDTO> {

    private String name;
    private BigDecimal price;
    private int quantity;
}
