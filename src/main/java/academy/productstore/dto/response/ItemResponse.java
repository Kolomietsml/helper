package academy.productstore.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Builder
public class ItemResponse extends RepresentationModel<ItemResponse> {

    private String name;
    private BigDecimal price;
    private int quantity;
}
