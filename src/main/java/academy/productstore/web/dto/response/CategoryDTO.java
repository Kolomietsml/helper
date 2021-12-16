package academy.productstore.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "categories")
@Getter
@Builder
public class CategoryDTO extends RepresentationModel<CategoryDTO> {

    private String name;
}