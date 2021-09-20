package academy.Motorola.service;

public interface CartService {

    Cart addProductToCart(Cart cart, long id);
    Cart removeProductFromCart(Cart cart, long id);
}