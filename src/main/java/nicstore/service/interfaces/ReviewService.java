package nicstore.service.interfaces;

import nicstore.models.Product;
import nicstore.models.ProductImage;
import nicstore.models.Review;
import nicstore.models.User;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> findReviewsByProduct(Product product);
    Optional<Review> findReviewByUserAndProduct(User user, Product product);

    @Transactional
    void saveReview(String comment, List<MultipartFile> files, Product product, User user);

    @Transactional
    List<ProductImage> saveReviewImage(List<MultipartFile> files, Long productId, Long userId);

    @Transactional
    void deleteReview(Review review);
}
