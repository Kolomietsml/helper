package academy.productstore.api;

import academy.productstore.assemblers.OrderAssembler;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.image.BufferedImage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AccountOrdersResource {

    private final OrderService orderService;
    private final OrderAssembler assembler;

    @GetMapping("/accounts/{account_id}/orders")
    public PagedModel<OrderResponse> getOrdersByAccount(@PathVariable("account_id") long accountId,
                                                        Pageable pageable,
                                                        PagedResourcesAssembler<Order> pagedResourcesAssembler) {
        Page<Order> orders = orderService.getAllOrdersByAccountId(accountId, pageable);
        return pagedResourcesAssembler.toModel(orders, assembler);
    }

    @GetMapping("/accounts/{account_id}/orders/{order_id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("account_id") long accountId,
                                                      @PathVariable("order_id") long orderId) {
        var order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @PostMapping("/accounts/{id}/orders")
    public ResponseEntity<OrderResponse> addOrder(@Valid @RequestBody OrderRequest orderRequest,
                                                  @PathVariable long id) {
        var order = orderService.addOrder(orderRequest, id);
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @SneakyThrows
    @GetMapping(value = "/accounts/{account_id}/orders/{order_id}/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCode(@PathVariable("account_id") long accountId,
                                                        @PathVariable("order_id") long orderId) {
        BufferedImage image = orderService.generateQRCode(orderId);
        return ResponseEntity.ok(image);
    }
}