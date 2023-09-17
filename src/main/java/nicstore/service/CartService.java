package nicstore.service;

import com.sun.xml.bind.v2.schemagen.episode.SchemaBindings;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.jar.asm.commons.Remapper;
import nicstore.Models.Cart;
import nicstore.Models.Product;
import nicstore.Models.User;
import nicstore.dto.auth.RegisterRequest;
import nicstore.dto.auth.UserInfoResponse;
import nicstore.dto.mapper.ProductMapper;
import nicstore.dto.product.CartContentResponse;
import nicstore.dto.product.ProductResponse;
import nicstore.exceprions.*;
import nicstore.repository.CartRepository;
import nicstore.repository.ProductRepository;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final ProductService productService;
    private final ModelMapper modelMapper;


    public Cart findCartByUser(User user) {
        return cartRepository.findCartByUser(user).orElseThrow(() -> new CartNotFoundException("Корзина не найдена"));
    }

    public CartContentResponse showCartContent() {
        User user = authService.getCurrentAuthorizedUser();
        Cart cart = findCartByUser(user);
        Map<ProductResponse, Integer> content = cart.getItems().entrySet().stream()
                .collect(Collectors.toMap(entry -> convertToProductResponse(entry.getKey()), Map.Entry::getValue));
        return new CartContentResponse(convertToUserInfoResponse(user), content);
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
    public void changeNumberProductInCart(Long productId, String operator) {
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
                }
                cart.decProduct(product);
            }
            break;
            default:
                throw new BadOperationException("Не известная команда");
        }
        cartRepository.save(cart);
    }


    private ProductResponse convertToProductResponse(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }

    private UserInfoResponse convertToUserInfoResponse(User user) {
        return modelMapper.map(user, UserInfoResponse.class);
    }

}

