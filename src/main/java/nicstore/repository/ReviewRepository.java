package nicstore.repository;

import nicstore.Models.Product;
import nicstore.Models.Review;
import nicstore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository  extends JpaRepository<Review, Long> {

    Optional<Review> findReviewByUserAndProduct(User user, Product product);

    List<Review> findReviewsByProduct(Product product);
}
