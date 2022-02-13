package academy.productstore.api;

import academy.productstore.assemblers.OrderAssembler;
import academy.productstore.domain.Account;
import academy.productstore.domain.Order;
import academy.productstore.dto.request.OrderRequest;
import academy.productstore.dto.response.OrderResponse;
import academy.productstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.image.BufferedImage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderResource {

    private final OrderService orderService;
    private final OrderAssembler assembler;

    @GetMapping("/users/{user_id}/orders")
    public PagedModel<OrderResponse> getOrdersByAccount(@PathVariable("user_id") long userId,
                                                        Pageable pageable,
                                                        PagedResourcesAssembler<Order> pagedResourcesAssembler) {
        Page<Order> orders = orderService.getAllOrdersByAccountId(userId, pageable);
        return pagedResourcesAssembler.toModel(orders, assembler);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable long id) {
        var order = orderService.getOrderById(id);
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> addOrder(@Valid @RequestBody OrderRequest orderRequest) {
        var order = orderService.addOrder(orderRequest, getPrincipalId());
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @SneakyThrows
    @GetMapping(value = "/orders/{id}/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCode(@PathVariable long id) {
        BufferedImage image = orderService.generateQRCode(id);
        return ResponseEntity.ok(image);
    }

    private long getPrincipalId() {
        return ((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}