package academy.Motorola.service;

import academy.Motorola.bean.Cart;

public interface CartService {

    Cart addProductToCart(Cart cart, long id);
    Cart removeProductFromCart(Cart cart, long id);
}