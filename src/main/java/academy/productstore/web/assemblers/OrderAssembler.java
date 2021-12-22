package academy.productstore.web.assemblers;

import academy.productstore.persistence.entity.DeliveryDetails;
import academy.productstore.persistence.entity.Order;
import academy.productstore.persistence.entity.OrderItem;
import academy.productstore.web.controllers.AccountOrdersController;
import academy.productstore.web.dto.DeliveryDetailsDTO;
import academy.productstore.web.dto.ItemDTO;
import academy.productstore.web.dto.OrderDTO;
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
                .deliveryDetails(getDeliveryDetailsDTO(order.getDeliveryDetails()))
                .build()
                .add(linkTo(methodOn(AccountOrdersController.class)
                        .getOrderById(order.getId())).withSelfRel().withType("GET"))
                .add(linkTo(methodOn(AccountOrdersController.class)
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

    private DeliveryDetailsDTO getDeliveryDetailsDTO(DeliveryDetails deliveryDetails) {
        return DeliveryDetailsDTO.builder()
                .firstname(deliveryDetails.getFirstName())
                .lastname(deliveryDetails.getLastname())
                .phone(deliveryDetails.getPhone())
                .street(deliveryDetails.getStreet())
                .build(deliveryDetails.getBuild())
                .apartment(deliveryDetails.getApartment())
                .build();
    }
}