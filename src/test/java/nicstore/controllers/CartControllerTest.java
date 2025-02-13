package nicstore.controllers;

import nicstore.dto.auth.UserResponse;
import nicstore.dto.product.CartContentResponse;
import nicstore.dto.product.ProductResponse;
import nicstore.service.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
class CartControllerTest {
    @InjectMocks
    private CartController cartController;

    @Mock
    private CartServiceImpl cartService;

    @Test
    void testGetCartContent() {
        // Создаем заглушку для ответа от сервиса корзины
        UserResponse userResponse = new UserResponse();
        Map<ProductResponse, Integer> productContent = new HashMap<>();
        ProductResponse productResponse = new ProductResponse();
        productContent.put(productResponse, 2);

        // Мокируем метод showCartContent
        Mockito.when(cartService.showCartContent()).thenReturn(new CartContentResponse(userResponse, productContent));

        // Вызываем метод контроллера
        ResponseEntity<CartContentResponse> responseEntity = cartController.getCartContent();

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(userResponse, Objects.requireNonNull(responseEntity.getBody()).getUserResponse());

        // Проверяем содержимое продуктов
        Map<ProductResponse, Integer> expectedProductContent = responseEntity.getBody().getItems();
        assertEquals(productContent.size(), expectedProductContent.size());

        for (Map.Entry<ProductResponse, Integer> entry : productContent.entrySet()) {
            ProductResponse expectedProduct = entry.getKey();
            Integer expectedQuantity = entry.getValue();
            assertTrue(expectedProductContent.containsKey(expectedProduct));
            assertEquals(expectedQuantity, expectedProductContent.get(expectedProduct));
        }
    }
}