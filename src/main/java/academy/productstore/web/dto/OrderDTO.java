package academy.productstore.web.dto;

import academy.productstore.persistence.entity.Status;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.List;

@Relation(collectionRelation = "order")
@Getter
@Builder
public class OrderDTO extends RepresentationModel<OrderDTO> {

    private String orderingDate;
    private String realizationDate;
    private BigDecimal amount;
    private Status status;
    private List<ItemDTO> items;
    private DeliveryDetailsDTO deliveryDetails;
}