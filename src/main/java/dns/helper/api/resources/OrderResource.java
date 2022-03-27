package dns.helper.api.resources;

import dns.helper.api.assemblers.OrderAssembler;
import dns.helper.api.dto.request.OrderRequest;
import dns.helper.api.dto.response.OrderResponse;
import dns.helper.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/v1/orders")
public class OrderResource {

    private final OrderService service;
    private final OrderAssembler assembler;

    @GetMapping()
    public CollectionModel<OrderResponse> getOrders() {
        return assembler.toCollectionModel(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable long id) {
        var order = service.getOrderId(id);
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @PostMapping()
    public ResponseEntity<OrderResponse> addOrder(@Valid @RequestBody OrderRequest request) {
        var order = service.addOrder(request);
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrderById(@Valid @RequestBody OrderRequest request,
                                                               @PathVariable long id) {
        var order = service.updateOrderById(request, id);
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrderById(@PathVariable long id) {
        service.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}