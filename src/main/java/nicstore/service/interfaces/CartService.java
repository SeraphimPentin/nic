package nicstore.service.interfaces;

import nicstore.Models.Cart;
import nicstore.Models.Product;
import nicstore.Models.User;
import nicstore.dto.product.CartContentResponse;

import javax.transaction.Transactional;

public interface CartService {

    boolean productInCart(Cart cart, Product product);

    void addProductToCart(Cart cart, Product product);

    void removeProductFromCart(Cart cart, Product product);

    void incProduct(Cart cart, Product product);

    void decProduct(Cart cart, Product product);

    Integer getQuantityProduct(Cart cart, Product product);

    Cart findCartByUser(User user);

    CartContentResponse showCartContent();

    @Transactional
    void addProductToCart(Long productId);
    @Transactional
    void changeQuantityProductInCart(Long productId, String operator);
}
