package dns.helper.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "orders")
@Getter
@Builder
public class OrderResponse extends RepresentationModel<OrderResponse> {

    private String date;
    private String content;
    private String url;
}