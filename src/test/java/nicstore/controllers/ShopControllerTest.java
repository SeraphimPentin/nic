package nicstore.controllers;

import nicstore.dto.product.*;
import nicstore.service.CartServiceImpl;
import nicstore.service.ShopServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShopControllerTest {

    @InjectMocks
    private ShopController shopController;

    @Mock
    private ShopServiceImpl shopService;

    @Mock
    private CartServiceImpl cartService;

    @Test
    void testAddProductToCart() {
        Long productId = 1L;
        doNothing().when(cartService).addProductToCart(productId);
        ResponseEntity<AddedProductToCartResponse> responseEntity = shopController.addProductToCart(productId);
        Mockito.verify(cartService).addProductToCart(productId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productId, Objects.requireNonNull(responseEntity.getBody()).getIdAddedProduct());
    }

    @Test
    void testChangeProductQuantityInCart() {
        Long productId = 1L;
        String operation = "inc";
        doNothing().when(cartService).changeQuantityProductInCart(productId, operation);
        ResponseEntity<Void> responseEntity = shopController.changeProductQuantityInCart(productId, operation);
        Mockito.verify(cartService, Mockito.times(1)).changeQuantityProductInCart(productId, operation);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetProductPage() {
        Long productId = 1L;
        ProductCharacteristicsResponse mockResponse = new ProductCharacteristicsResponse();
        when(shopService.getProductPage(productId)).thenReturn(mockResponse);
        ResponseEntity<ProductCharacteristicsResponse> responseEntity = shopController.getProductPage(productId);
        Mockito.verify(shopService, Mockito.times(1)).getProductPage(productId);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(mockResponse, responseEntity.getBody());
    }

    @Test
    void testGetReviewDTOForEditing() {
        Long productId = 1L;
        ReviewResponse mockReviewResponse = new ReviewResponse();
        when(shopService.getReviewDTOForEditing(productId)).thenReturn(mockReviewResponse);
        ResponseEntity<ReviewResponse> responseEntity = shopController.getReviewDTOForEditing(productId);
        Mockito.verify(shopService, Mockito.times(1)).getReviewDTOForEditing(productId);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(mockReviewResponse, responseEntity.getBody());
    }

    @Test
    void testDeleteRating() {
        Long productId = 1L;
        doNothing().when(shopService).deleteRating(productId);
        ResponseEntity<?> responseEntity = shopController.deleteRating(productId);
        Mockito.verify(shopService, Mockito.times(1)).deleteRating(productId);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testDeleteReview() {
        Long productId = 1L;
        doNothing().when(shopService).deleteReview(productId);
        ResponseEntity<?> responseEntity = shopController.deleteReview(productId);
        Mockito.verify(shopService, Mockito.times(1)).deleteReview(productId);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}