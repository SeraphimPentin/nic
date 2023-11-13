package nicstore.service.interfaces;

import nicstore.Models.Product;
import nicstore.Models.Rating;
import nicstore.Models.User;

import javax.transaction.Transactional;
import java.util.List;

public interface RatingService {

    public List<Rating> findRatingsByProduct(Product product);

    public Rating findRatingByUserAndProduct(User user, Product product);

    public Integer findRatingsNumberByProduct(Product product);

    public Double findAverageRatingByProduct(Product product);

    @Transactional
    public void deleteRating(Rating rating);

    @Transactional
    public void updateRatingValueById(Rating rating, Integer ratingValue);

    @Transactional
    public void saveRating(User user, Product product, Integer ratingValue);
}
