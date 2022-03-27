package dns.helper.api.assemblers;

import dns.helper.api.resources.LinkResource;
import dns.helper.db.domain.Link;
import dns.helper.api.dto.request.LinkRequest;
import dns.helper.api.dto.response.LinkResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LinkAssembler implements RepresentationModelAssembler<Link, LinkResponse> {

    @Override
    public LinkResponse toModel(Link entity) {
        var response = LinkResponse.builder()
                .url(entity.getUrl())
                .title(entity.getTitle())
                .build();

        response.add(linkTo(methodOn(LinkResource.class)
                .getLinkById(entity.getId()))
                .withSelfRel()
                .withType("GET"));

        response.add(linkTo(methodOn(LinkResource.class)
                .updateLinkById(new LinkRequest(), entity.getId()))
                .withRel("update")
                .withType("PUT"));

        response.add(linkTo(methodOn(LinkResource.class)
                .deleteLinkById(entity.getId()))
                .withRel("delete")
                .withType("DELETE"));

        return response;
    }

    @Override
    public CollectionModel<LinkResponse> toCollectionModel(Iterable<? extends Link> entities) {
        CollectionModel<LinkResponse> links = RepresentationModelAssembler.super.toCollectionModel(entities);

        links.add(linkTo(methodOn(LinkResource.class)
                .getLinks())
                .withSelfRel()
                .withType("GET"));

        links.add(linkTo(methodOn(LinkResource.class)
        .addLink(new LinkRequest()))
        .withRel("add")
        .withType("POST"));

        return links;
    }
}