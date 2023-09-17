package nicstore.service;

import lombok.RequiredArgsConstructor;
import nicstore.Models.Product;
import nicstore.Models.Rating;
import nicstore.Models.User;
import nicstore.repository.RatingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;


    public List<Rating> findRatingsByProduct(Product product) {
        return ratingRepository.findRatingsByProduct(product);
    }

    public Double findAverageRatingByProduct(Product product) {
        return ratingRepository.calculateAverageRatingByProductId(product.getId());
    }

    @Transactional
    public void deleteRating(Rating rating) {
        ratingRepository.delete(rating);
    }

    @Transactional
    public void updateRatingValueById(Rating rating, Integer ratingValue) {
        ratingRepository.updateRatingValueById(rating.getId(), ratingValue);
    }

    @Transactional
    public void saveRating(User user, Product product, Integer ratingValue) {
        ratingRepository.save(
                Rating.builder()
                        .user(user)
                        .product(product)
                        .value(ratingValue)
                        .build()
        );
    }
}
