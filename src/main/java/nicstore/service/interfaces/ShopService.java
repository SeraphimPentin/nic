package nicstore.service.interfaces;

import nicstore.dto.cart.ResponseOrder;
import nicstore.dto.cart.ResponseTotalOrder;
import nicstore.dto.product.CartContentResponse;
import nicstore.dto.product.ProductCharacteristicsResponse;
import nicstore.dto.product.ReviewResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShopService {

    ProductCharacteristicsResponse getProductPage(Long productId);

    void setReviewOnProduct(Long productId, String comment, List<MultipartFile> images);

    ReviewResponse getReviewDTOForEditing(Long productId);

    void editExistingReview(Long productId, String comment, List<MultipartFile> images);

    void deleteReview(Long productId);


    void setRatingOnProduct(Long productId, Integer value);


    void deleteRating(Long productId);

    ResponseOrder makeOrder();

    boolean checkProductsAvailability(CartContentResponse cartContent);

    void updatingQuantityProductsInStock(CartContentResponse content);

    ResponseTotalOrder createdOrderList(CartContentResponse content);

    ResponseOrder sendOrderConfirmationEmail(CartContentResponse cartContent);
}
