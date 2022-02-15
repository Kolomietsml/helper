package academy.productstore.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "products")
@Getter
@Builder
public class ProductResponse extends RepresentationModel<ProductResponse> {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private CategoryResponse category;
}