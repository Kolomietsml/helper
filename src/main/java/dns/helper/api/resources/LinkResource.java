package dns.helper.api.resources;

import dns.helper.api.assemblers.LinkAssembler;
import dns.helper.api.dto.request.LinkRequest;
import dns.helper.api.dto.response.LinkResponse;
import dns.helper.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/v1/links")
public class LinkResource {

    private final LinkService service;
    private final LinkAssembler assembler;

    @GetMapping()
    public CollectionModel<LinkResponse> getLinks() {
        return assembler.toCollectionModel(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinkResponse> getLinkById(@PathVariable long id) {
        var link = service.getLinkById(id);
        return ResponseEntity.ok(assembler.toModel(link));
    }

    @PostMapping()
    public ResponseEntity<LinkResponse> addLink(@Valid @RequestBody LinkRequest request) {
        var link = service.addLink(request);
        return ResponseEntity.ok(assembler.toModel(link));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LinkResponse> updateLinkById(@Valid @RequestBody LinkRequest request,
                                                         @PathVariable long id) {
        var link = service.updateLinkById(request, id);
        return ResponseEntity.ok(assembler.toModel(link));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLinkById(@PathVariable long id) {
        service.deleteLinkById(id);
        return ResponseEntity.noContent().build();
    }
}