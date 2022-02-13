package academy.productstore.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "categories")
@Getter
@Builder
public class CategoryResponse extends RepresentationModel<CategoryResponse> {

    private String name;
}