package academy.productstore.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "details")
@Getter
@Builder
public class OrderDetailsDTO {

    private String title;
    private int quantity;
    private BigDecimal price;
}