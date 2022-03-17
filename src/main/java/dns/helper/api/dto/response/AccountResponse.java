package dns.helper.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "accounts")
@Getter
@Builder
public class AccountResponse extends RepresentationModel<AccountResponse> {

    private String email;
}