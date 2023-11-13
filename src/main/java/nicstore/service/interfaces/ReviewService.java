package nicstore.service.interfaces;

import nicstore.Models.Product;
import nicstore.Models.ProductImage;
import nicstore.Models.Review;
import nicstore.Models.User;
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
