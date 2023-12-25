package nicstore.service;

import nicstore.models.*;
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
    ShopServiceImpl shopService;

    @Mock
    AuthServiceImpl authService;

    @Mock
    ReviewServiceImpl reviewService;

    @Mock
    ProductServiceImpl productService;

    @Mock
    RatingServiceImpl ratingService;

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
        Product product = new Product();
        User user = new User();
        Integer value = 5;
        ratingService.saveRating(user, product, value);
        verify(ratingService).saveRating(user, product, value);
    }

    @Test
    void testDeleteRating() {
        Long id = 123L;
        Product product = new Product();
        User user = new User();
        Rating rating = new Rating();
        when(productService.findProductById(id)).thenReturn(product);
        when(authService.getCurrentAuthorizedUser()).thenReturn(user);
        when(ratingService.findRatingByUserAndProduct(user, product)).thenReturn(rating);
        shopService.deleteRating(id);
        verify(productService).findProductById(id);
        verify(authService).getCurrentAuthorizedUser();
        verify(ratingService).findRatingByUserAndProduct(user, product);
        verify(ratingService).deleteRating(rating);
    }
}