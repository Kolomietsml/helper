package academy.productstore.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.List;

@Relation(collectionRelation = "cart")
@Getter
@Builder
public class CartDTO extends RepresentationModel<CartDTO> {

    private List<CartDetailsDTO> products;
    private BigDecimal amount;
}