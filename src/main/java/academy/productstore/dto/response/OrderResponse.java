package academy.productstore.dto.response;

import academy.productstore.domain.Status;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.List;

@Relation(collectionRelation = "order")
@Getter
@Builder
public class OrderResponse extends RepresentationModel<OrderResponse> {

    private String orderingDate;
    private String realizationDate;
    private BigDecimal amount;
    private Status status;
    private List<ItemResponse> items;
}