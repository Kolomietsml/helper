package dns.helper.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "emergencies")
@Getter
@Builder
public class EmergencyResponse extends RepresentationModel<EmergencyResponse> {

    private String phone;
    private String title;
}