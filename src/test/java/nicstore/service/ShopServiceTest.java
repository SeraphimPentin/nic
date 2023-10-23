package nicstore.service;

import nicstore.Models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @InjectMocks
    ShopService shopService;

    @Mock
    AuthService authService;

    @Mock
    ReviewService reviewService;

    @Mock
    ProductService productService;

    @Test
    void testSetReviewOnProduct() {
        Long productId = 28L;
        String comment = "any comment";
        List<MultipartFile> files = new ArrayList<>();
        Product product = new Product();
        when(productService.findProductById(productId)).thenReturn(product);
        User user = new User();
        when(authService.getCurrentAuthorizedUser()).thenReturn(user);
        shopService.setReviewOnProduct(productId, comment, files);
        verify(authService).getCurrentAuthorizedUser();
        verify(reviewService).saveReview(comment, files, product, user);
    }

    @Test
    void testDeleteReview() {
        Product product = new Product();
        product.setId(1L);
        User user = new User();
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        when(authService.getCurrentAuthorizedUser()).thenReturn(user);
        when(productService.findProductById(product.getId())).thenReturn(product);
        when(reviewService.findReviewByUserAndProduct(user, product)).thenReturn(Optional.of(review));
        assertDoesNotThrow(() -> shopService.deleteReview(product.getId()));
        verify(reviewService).deleteReview(review);
    }

    @Test
    void testSetRatingOnProduct() {
    }

    @Test
    void testDeleteRating() {
    }
}