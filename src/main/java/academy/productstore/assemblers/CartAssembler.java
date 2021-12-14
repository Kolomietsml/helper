package academy.productstore.assemblers;

import academy.productstore.controllers.CartsController;
import academy.productstore.controllers.ProductsController;
import academy.productstore.dto.CartDTO;
import academy.productstore.dto.CartDetailsDTO;
import academy.productstore.entity.Product;
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
       return CartDTO.builder()
                .products(getDetails(cart.getItems()))
                .amount(cart.getTotal())
                .build()
                .add(linkTo(methodOn(CartsController.class).showCart()).withSelfRel().withType("GET"));
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