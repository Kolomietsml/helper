package academy.productstore.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "products")
@Getter
@Builder
public class ProductDTO extends RepresentationModel<ProductDTO> {

    private String name;
    private String description;
    private BigDecimal price;
    private CategoryDTO category;
}