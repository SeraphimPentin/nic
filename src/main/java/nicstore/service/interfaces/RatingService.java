package nicstore.service.interfaces;

import nicstore.models.Product;
import nicstore.models.Rating;
import nicstore.models.User;

import javax.transaction.Transactional;
import java.util.List;

public interface RatingService {

    List<Rating> findRatingsByProduct(Product product);

    Rating findRatingByUserAndProduct(User user, Product product);

    Integer findRatingsNumberByProduct(Product product);

    Double findAverageRatingByProduct(Product product);

    @Transactional
    void deleteRating(Rating rating);

    @Transactional
    void updateRatingValueById(Rating rating, Integer ratingValue);

    @Transactional
    void saveRating(User user, Product product, Integer ratingValue);
}
