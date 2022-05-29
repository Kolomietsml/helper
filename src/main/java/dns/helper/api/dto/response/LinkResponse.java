package dns.helper.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "links")
@Getter
@Builder
public class LinkResponse extends RepresentationModel<LinkResponse> {

    private String url;
    private String title;
    private String description;
    private String command;
}