package academy.productstore.assemblers;

import academy.productstore.domain.Order;
import academy.productstore.domain.OrderItem;
import academy.productstore.controllers.OrdersController;
import academy.productstore.dto.ItemDTO;
import academy.productstore.dto.OrderDTO;
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
                .items(getDetails(order.getItems()))
                .build()
//                .add(linkTo(methodOn(AccountOrdersController.class)
//                        .getOrderById(order.getId())).withSelfRel().withType("GET"))
                .add(linkTo(methodOn(OrdersController.class)
                        .generateQRCode(order.getId())).withRel("qr_code").withType("GET"));
    }

    @Override
    public CollectionModel<OrderDTO> toCollectionModel(Iterable<? extends Order> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

    private List<ItemDTO> getDetails(Set<OrderItem> items) {
        return items.stream().map(this::getDetail).collect(Collectors.toList());
    }

    private ItemDTO getDetail(OrderItem orderItem) {
        return ItemDTO.builder()
                .name(orderItem.getTitle())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }
}