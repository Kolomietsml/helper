package academy.productstore.service;

public interface CartService {

    Cart addProductToCart(Cart cart, long id);
    Cart removeProductFromCart(Cart cart, long id);
    Cart removeAll(Cart cart);
}