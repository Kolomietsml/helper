package academy.productstore.web.assemblers;

import academy.productstore.persistence.entity.Product;
import academy.productstore.service.Cart;
import academy.productstore.web.controllers.AccountOrdersController;
import academy.productstore.web.controllers.CartsController;
import academy.productstore.web.controllers.ProductsController;
import academy.productstore.web.dto.CartDTO;
import academy.productstore.web.dto.ItemDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartAssembler implements RepresentationModelAssembler<Cart, CartDTO> {

    @Override
    public CartDTO toModel(Cart cart) {
       var cartDTO = CartDTO.builder()
               .items(getDetails(cart.getItems()))
               .amount(cart.getTotal())
               .build();

       cartDTO.add(linkTo(methodOn(CartsController.class).showCart()).withSelfRel().withType("GET"));

       if (!cart.getItems().isEmpty()) {
           cartDTO.add(linkTo(AccountOrdersController.class).slash("checkout").withRel("checkout").withType("GET"));
           cartDTO.add(linkTo(CartsController.class).slash("/").withRel("empty_cart").withType("DELETE"));
       }

       return cartDTO;
    }

    private List<ItemDTO> getDetails(Map<Product, Integer> items) {
        return items.entrySet().stream().map(this::getDetail).collect(Collectors.toList());
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