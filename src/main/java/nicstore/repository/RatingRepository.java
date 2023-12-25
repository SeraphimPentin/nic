package nicstore.repository;

import nicstore.models.Product;
import nicstore.models.Rating;
import nicstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository  extends JpaRepository<Rating, Long> {
    List<Rating> findRatingsByProduct(Product product);

    Rating findRatingByUserAndProduct(User user, Product product);

    @Modifying
    @Query("UPDATE Rating r SET r.value = ?2 WHERE r.id = ?1")
    void updateRatingValueById(Long id, Integer value);

    @Query("SELECT AVG(r.value) FROM Rating r WHERE r.product.id = :productId")
    Double calculateAverageRatingByProductId(@Param("productId") Long productId);

    Integer countRatingsByProduct(Product product);
}
