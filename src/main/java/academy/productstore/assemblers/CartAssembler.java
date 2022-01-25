package academy.productstore.assemblers;

import academy.productstore.domain.Product;
import academy.productstore.service.cart.Cart;
import academy.productstore.controllers.CartsController;
import academy.productstore.controllers.ProductsController;
import academy.productstore.dto.CartDTO;
import academy.productstore.dto.ItemDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartAssembler implements RepresentationModelAssembler<Cart, CartDTO> {

    @Override
    public CartDTO toModel(Cart cart) {
       var cartDTO = CartDTO.builder()
               .items(cart.getItems().entrySet().stream().map(this::getDetail).collect(Collectors.toList()))
               .amount(cart.getAmount())
               .build();

       cartDTO.add(linkTo(methodOn(CartsController.class).showCart()).withSelfRel().withType("GET"));

       if (!cart.getItems().isEmpty()) {
           cartDTO.add(linkTo(CartsController.class).slash("/").withRel("empty_cart").withType("DELETE"));
       }

       return cartDTO;
    }

    private ItemDTO getDetail(Map.Entry<Product, Integer> entry) {
        return ItemDTO.builder()
                .name(entry.getKey().getName())
                .price(entry.getKey().getPrice())
                .quantity(entry.getValue())
                .build()
                .add(linkTo(methodOn(ProductsController.class)
                        .getProduct(entry.getKey().getId())).withSelfRel().withType("GET"))
                .add(linkTo(CartsController.class).slash("/").withRel("remove_from_cart").withType("PUT"));
    }
}