package academy.productstore.web.assemblers;

import academy.productstore.persistence.entity.Order;
import academy.productstore.persistence.entity.OrderDetails;
import academy.productstore.web.controllers.OrdersController;
import academy.productstore.web.dto.response.OrderDTO;
import academy.productstore.web.dto.response.OrderDetailsDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderAssembler implements RepresentationModelAssembler<Order, OrderDTO> {

    @Override
    public OrderDTO toModel(Order order) {
        return OrderDTO.builder()
                .orderingDate(order.getOrderingDate())
                .realizationDate(order.getRealizationDate())
                .amount(order.getAmount())
                .status(order.getStatus())
                .details(getDetails(order.getDetails()))
                .build()
                .add(linkTo(methodOn(OrdersController.class)
                        .getOrderById(order.getId())).withSelfRel().withType("GET"))
                .add(linkTo(methodOn(OrdersController.class)
                        .generateQRCode(order.getId())).withRel("qr_code").withType("GET"));
    }

    @Override
    public CollectionModel<OrderDTO> toCollectionModel(Iterable<? extends Order> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

    private List<OrderDetailsDTO> getDetails(Set<OrderDetails> items) {
        return items.stream().map(this::getDetail).collect(Collectors.toList());
    }

    private OrderDetailsDTO getDetail(OrderDetails orderDetails) {
        return OrderDetailsDTO.builder()
                .title(orderDetails.getTitle())
                .quantity(orderDetails.getQuantity())
                .price(orderDetails.getPrice())
                .build();
    }
}