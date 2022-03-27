package dns.helper.api.assemblers;

import dns.helper.api.dto.request.EmergencyRequest;
import dns.helper.api.dto.response.EmergencyResponse;
import dns.helper.api.resources.EmergencyResource;
import dns.helper.db.domain.Emergency;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmergencyAssembler implements RepresentationModelAssembler<Emergency, EmergencyResponse> {

    @Override
    public EmergencyResponse toModel(Emergency entity) {
        var response = EmergencyResponse.builder()
                .phone(entity.getPhone())
                .title(entity.getTitle())
                .build();

        response.add(linkTo(methodOn(EmergencyResource.class)
                .getEmergencyById(entity.getId()))
                .withSelfRel()
                .withType("GET"));

        response.add(linkTo(methodOn(EmergencyResource.class)
                .updateEmergencyById(new EmergencyRequest(), entity.getId()))
                .withRel("update")
                .withType("PUT"));

        response.add(linkTo(methodOn(EmergencyResource.class)
                .deleteEmergencyById(entity.getId()))
                .withRel("delete")
                .withType("DELETE"));

        return response;
    }

    @Override
    public CollectionModel<EmergencyResponse> toCollectionModel(Iterable<? extends Emergency> entities) {
        CollectionModel<EmergencyResponse> emergencies = RepresentationModelAssembler.super.toCollectionModel(entities);

        emergencies.add(linkTo(methodOn(EmergencyResource.class)
                .getEmergencies())
                .withSelfRel()
                .withType("GET"));

        emergencies.add(linkTo(methodOn(EmergencyResource.class)
                .addEmergency(new EmergencyRequest()))
                .withRel("add")
                .withType("POST"));

        return emergencies;
    }
}