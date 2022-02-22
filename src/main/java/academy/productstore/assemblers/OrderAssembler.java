package academy.productstore.assemblers;

import academy.productstore.api.AccountOrdersResource;
import academy.productstore.domain.Order;
import academy.productstore.domain.OrderItem;
import academy.productstore.dto.response.ItemResponse;
import academy.productstore.dto.response.OrderResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderAssembler implements RepresentationModelAssembler<Order, OrderResponse> {

    @Override
    public OrderResponse toModel(Order order) {
        var orderResponse = OrderResponse.builder()
                .orderingDate(order.getOrderingDate())
                .realizationDate(order.getRealizationDate())
                .amount(order.getAmount())
                .status(order.getStatus())
                .items(getDetails(order.getItems()))
                .build();

        orderResponse.add(linkTo(methodOn(AccountOrdersResource.class)
                .getOrderById(order.getAccountId(), order.getId()))
                .withSelfRel()
                .withType("GET"));

        orderResponse.add(linkTo(methodOn(AccountOrdersResource.class)
                .generateQRCode(order.getAccountId(), order.getId()))
                .withRel("qr_code")
                .withType("GET"));

        return orderResponse;
    }

    @Override
    public CollectionModel<OrderResponse> toCollectionModel(Iterable<? extends Order> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

    private List<ItemResponse> getDetails(Set<OrderItem> items) {
        return items.stream().map(this::getDetail).collect(Collectors.toList());
    }

    private ItemResponse getDetail(OrderItem orderItem) {
        return ItemResponse.builder()
                .name(orderItem.getTitle())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }
}