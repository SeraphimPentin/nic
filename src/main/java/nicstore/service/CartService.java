package nicstore.service;

import lombok.RequiredArgsConstructor;
import nicstore.Models.Cart;
import nicstore.Models.Product;
import nicstore.Models.User;
import nicstore.dto.auth.UserResponse;
import nicstore.dto.mapper.ConvertorMapper;
import nicstore.dto.product.CartContentResponse;
import nicstore.dto.product.ProductResponse;
import nicstore.exceptions.*;
import nicstore.exceptions.auth.UnauthorizedUserException;
import nicstore.repository.CartRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final ProductService productService;
    private final ConvertorMapper mapper;


    public Cart findCartByUser(User user) {
        return cartRepository.findCartByUser(user).orElseThrow(() -> new CartNotFoundException("Корзина не найдена"));
    }

    public CartContentResponse showCartContent() {
        User user = authService.getCurrentAuthorizedUser();
        if (user == null) {
            throw new UnauthorizedUserException("Пользователь не авторизован");
        }
        Cart cart = findCartByUser(user);

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Корзина пуста");
        }
        Map<ProductResponse, Integer> content = new HashMap<>();

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            ProductResponse productShortResponse = mapper.getMapper().map(entry.getKey(), ProductResponse.class);
            Integer value = entry.getValue();
            content.put(productShortResponse, value);
        }
        return new CartContentResponse(mapper.getMapper().map(user, UserResponse.class), content);
    }

    @Transactional
    public void addProductToCart(Long productId) {
        User user = authService.getCurrentAuthorizedUser();
        Cart cart = findCartByUser(user);
        Product product = productService.findProductById(productId);
        if (cart.productInCart(product)) {
            throw new ProductAlreadyInCartException("Товар уже в корзине");
        } else {
            cart.addProductToCart(product);
            cartRepository.save(cart);
        }
    }

    @Transactional
    public void changeQuantityProductInCart(Long productId, String operator) {
        User user = authService.getCurrentAuthorizedUser();
        Cart cart = findCartByUser(user);
        Product product = productService.findProductById(productId);
        Integer quantityProductInCart = cart.getQuantityProduct(product);
        switch (operator) {
            case "inc": {
                if (product.getQuantity() > quantityProductInCart) {
                    cart.incProduct(product);
                } else {
                    throw new ProductOutInStockException("Товара нет в наличии");
                }
            }
            break;
            case "dec": {
                if (quantityProductInCart == 1) {
                    cart.removeProductFromCart(product);
                } else {
                    cart.decProduct(product);
                }
            }
            break;
            default:
                throw new BadOperationException("Не известная команда");
        }
        cartRepository.save(cart);
    }
}

