package academy.productstore.service.cart;

public interface CartService {

    Cart getCart();
    Cart addItem(long id);
    Cart removeItem(long id);
    Cart removeAllItems();
}