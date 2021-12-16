package academy.productstore.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "products")
@Getter
@Builder
public class AccountDTO extends RepresentationModel<AccountDTO> {

    private String firstname;
    private String lastname;
    private String phone;
    private String email;
}