package nicstore.service;

import nicstore.Models.Cart;
import nicstore.Models.Product;
import nicstore.Models.User;
import nicstore.exceptions.ProductAlreadyInCartException;
import nicstore.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductServiceImpl productService;
    @Mock
    private AuthServiceImpl authService;


    @Test
    public void testAddProductToCart() {
        Long productId = 111L;
        User user = new User();
        Product product = new Product();
        Cart cart = new Cart();
        Map<Product, Integer> items = new HashMap<>();
        cart.setItems(items);
        when(authService.getCurrentAuthorizedUser()).thenReturn(user);
        when(cartRepository.findCartByUser(user)).thenReturn(Optional.of(cart));
        when(productService.findProductById(productId)).thenReturn(product);
        assertDoesNotThrow(() -> cartService.addProductToCart(productId));
        verify(cartRepository).save(cart);
        assertTrue(items.containsKey(product));
        assertEquals(1, items.get(product));
        items.put(product, 1);
        cart.setItems(items);
        when(authService.getCurrentAuthorizedUser()).thenReturn(user);
        when(cartRepository.findCartByUser(user)).thenReturn(Optional.of(cart));
        when(productService.findProductById(productId)).thenReturn(product);
        assertThrows(ProductAlreadyInCartException.class, () -> cartService.addProductToCart(productId));
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    public void testChangeQuantityProductInCartInc() {
        Long productId = 222L;
        String operation = "inc";
        User user = new User();
        Product product = new Product();
        product.setQuantity(3);
        Cart cart = new Cart();
        Map<Product, Integer> items = new HashMap<>();
        items.put(product, 1);
        cart.setItems(items);
        when(authService.getCurrentAuthorizedUser()).thenReturn(user);
        when(cartRepository.findCartByUser(user)).thenReturn(Optional.of(cart));
        when(productService.findProductById(productId)).thenReturn(product);
        assertDoesNotThrow(() -> cartService.changeQuantityProductInCart(productId, operation));
        verify(cartRepository).save(cart);
        assertEquals(2, items.get(product));
    }

    @Test
    public void testChangeQuantityProductInCartDec() {
        Long productId = 333L;
        String operation = "dec";
        User user = new User();
        Product product = new Product();
        product.setQuantity(3);
        Cart cart = new Cart();
        Map<Product, Integer> items = new HashMap<>();
        items.put(product, 2);
        cart.setItems(items);
        when(authService.getCurrentAuthorizedUser()).thenReturn(user);
        when(cartRepository.findCartByUser(user)).thenReturn(Optional.of(cart));
        when(productService.findProductById(productId)).thenReturn(product);
        assertDoesNotThrow(() -> cartService.changeQuantityProductInCart(productId, operation));
        verify(cartRepository).save(cart);
        assertEquals(1, items.get(product));
    }

    @Test
    public void testChangeProductQuantityInCart_RemoveItemFromCart() {
        Long productId = 444L;
        String operation = "dec";
        User user = new User();
        Product product = new Product();
        product.setQuantity(3);
        Cart cart = new Cart();
        Map<Product, Integer> items = new HashMap<>();
        items.put(product, 1);
        cart.setItems(items);
        when(authService.getCurrentAuthorizedUser()).thenReturn(user);
        when(cartRepository.findCartByUser(user)).thenReturn(Optional.of(cart));
        when(productService.findProductById(productId)).thenReturn(product);
        assertDoesNotThrow(() -> cartService.changeQuantityProductInCart(productId, operation));
        verify(cartRepository).save(cart);
        assertFalse(items.containsKey(product));
    }
}