package dns.helper.api.resources;

import dns.helper.api.assemblers.EmergencyAssembler;
import dns.helper.api.dto.request.EmergencyRequest;
import dns.helper.api.dto.response.EmergencyResponse;
import dns.helper.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/v1/emergencies")
public class EmergencyResource {

    private final EmergencyService service;
    private final EmergencyAssembler assembler;

    @GetMapping()
    public CollectionModel<EmergencyResponse> getEmergencies() {
        return assembler.toCollectionModel(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmergencyResponse> getEmergencyById(@PathVariable long id) {
        var emergency = service.getEmergencyById(id);
        return ResponseEntity.ok(assembler.toModel(emergency));
    }

    @PostMapping()
    public ResponseEntity<EmergencyResponse> addEmergency(@Valid @RequestBody EmergencyRequest request) {
        var emergencyPhone = service.addEmergency(request);
        return ResponseEntity.ok(assembler.toModel(emergencyPhone));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmergencyResponse> updateEmergencyById(@Valid @RequestBody EmergencyRequest request,
                                                                  @PathVariable long id) {
        var emergencyPhone = service.updateEmergencyById(request, id);
        return ResponseEntity.ok(assembler.toModel(emergencyPhone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmergencyById(@PathVariable long id) {
        service.deleteEmergencyById(id);
        return ResponseEntity.noContent().build();
    }
}