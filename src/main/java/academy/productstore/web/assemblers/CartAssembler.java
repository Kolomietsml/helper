package academy.productstore.web.assemblers;

import academy.productstore.web.controllers.CartsController;
import academy.productstore.web.controllers.OrdersController;
import academy.productstore.web.controllers.ProductsController;
import academy.productstore.web.dto.response.CartDTO;
import academy.productstore.web.dto.response.CartDetailsDTO;
import academy.productstore.persistence.entity.Product;
import academy.productstore.service.Cart;
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
               .products(getDetails(cart.getItems()))
               .amount(cart.getTotal())
               .build();
       cartDTO.add(linkTo(methodOn(CartsController.class).showCart()).withSelfRel().withType("GET"));
       if (!cart.getItems().isEmpty()) {
           cartDTO.add(linkTo(methodOn(OrdersController.class).addOrder()).withRel("order").withType("POST"));
       }
       return cartDTO;
    }

    private List<CartDetailsDTO> getDetails(Map<Product, Integer> items) {
        return items.entrySet().stream().map(this::getDetail).collect(Collectors.toList());
    }

    private CartDetailsDTO getDetail(Map.Entry<Product, Integer> entry) {
        return CartDetailsDTO.builder()
                .name(entry.getKey().getName())
                .price(entry.getKey().getPrice())
                .quantity(entry.getValue())
                .build()
                .add(linkTo(methodOn(ProductsController.class)
                        .getProduct(entry.getKey().getId())).withSelfRel().withType("GET"))
                .add(linkTo(methodOn(CartsController.class)
                        .removeProductFromCart(entry.getKey().getId())).withRel("remove_from_cart").withType("PUT"));
    }
}