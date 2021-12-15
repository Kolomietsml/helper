package academy.productstore.service;

import academy.productstore.persistence.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final ProductService productService;

    public CartServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Cart addProductToCart(Cart cart, long id) {
        Product product = productService.getProductById(id);
        if (cart.getItems().containsKey(product)) {
            int qty = cart.getItems().get(product);
            cart.getItems().replace(product, qty + 1);
        } else {
            cart.getItems().put(product, 1);
        }
        return cart;
    }

    @Override
    public Cart removeProductFromCart(Cart cart, long id) {
        Product product = productService.getProductById(id);
        if (cart.getItems().containsKey(product)) {
            int qty = cart.getItems().get(product);
            cart.getItems().replace(product, qty - 1);
            if (cart.getItems().get(product) <= 0) {
                cart.getItems().remove(product);
            }
        }
        return cart;
    }
}