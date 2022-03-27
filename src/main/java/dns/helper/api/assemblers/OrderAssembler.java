package dns.helper.api.assemblers;

import dns.helper.api.resources.OrderResource;
import dns.helper.db.domain.Order;
import dns.helper.api.dto.request.OrderRequest;
import dns.helper.api.dto.response.OrderResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderAssembler implements RepresentationModelAssembler<Order, OrderResponse> {

    @Override
    public OrderResponse toModel(Order entity) {
        var response = OrderResponse.builder()
                .date(entity.getDate())
                .content(entity.getContent())
                .url(entity.getUrl())
                .build();

        response.add(linkTo(methodOn(OrderResource.class)
                .getOrderById(entity.getId()))
                .withSelfRel()
                .withType("GET"));

        response.add(linkTo(methodOn(OrderResource.class)
                .updateOrderById(new OrderRequest(), entity.getId()))
                .withRel("update")
                .withType("PUT"));

        response.add(linkTo(methodOn(OrderResource.class)
                .deleteOrderById(entity.getId()))
                .withRel("delete")
                .withType("DELETE"));

        return response;
    }

    @Override
    public CollectionModel<OrderResponse> toCollectionModel(Iterable<? extends Order> entities) {
        CollectionModel<OrderResponse> orders = RepresentationModelAssembler.super.toCollectionModel(entities);

        orders.add(linkTo(methodOn(OrderResource.class)
                .getOrders())
                .withSelfRel()
                .withType("GET"));

        orders.add(linkTo(methodOn(OrderResource.class)
        .addOrder(new OrderRequest()))
        .withRel("add")
        .withType("POST"));

        return orders;
    }
}