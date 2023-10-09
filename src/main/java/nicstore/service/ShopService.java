package nicstore.service;

import lombok.RequiredArgsConstructor;
import nicstore.Models.Product;
import nicstore.Models.Rating;
import nicstore.Models.Review;
import nicstore.Models.User;
import nicstore.dto.mapper.ConvertorMapper;
import nicstore.dto.product.ProductCharacteristicsResponse;
import nicstore.dto.product.ReviewResponse;
import nicstore.exceptions.ReviewNotExistingException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final CategoryService categoryService;
    private final AuthService authService;
    private final ProductService productService;
    private final RatingService ratingService;
    private final ReviewService reviewService;
    private final ConvertorMapper mapper;


    public ProductCharacteristicsResponse getProductPage(Long productId) {
        Product product = productService.findProductById(productId);
        ProductCharacteristicsResponse productCharacteristicsResponse = mapper.getMapper().map(product, ProductCharacteristicsResponse.class);
        productCharacteristicsResponse.setNumberRatings(ratingService.findRatingsNumberByProduct(product));
        productCharacteristicsResponse.setAverageRating(ratingService.findAverageRatingByProduct(product));

        List<Review> reviewsList = reviewService.findReviewsByProduct(product);
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (Review review : reviewsList) {
            ReviewResponse reviewResponse = mapper.getMapper().map(review, ReviewResponse.class);
            User author = review.getUser();
            reviewResponse.setUser(author.getFirstname() + " " + author.getLastname());
            reviewResponses.add(reviewResponse);
        }
        productCharacteristicsResponse.setReviews(reviewResponses);
        productCharacteristicsResponse.setNumberReviews(reviewsList.size());
        return productCharacteristicsResponse;
    }

    public void setReviewOnProduct(Long productId, String comment, List<MultipartFile> images) {
        Product product = productService.findProductById(productId);
        User user = authService.getCurrentAuthorizedUser();
        reviewService.saveReview(comment, images, product, user);
    }

    public ReviewResponse getReviewDTOForEditing(Long productId) {
        Product product = productService.findProductById(productId);
        User user = authService.getCurrentAuthorizedUser();
        Review existingReview = reviewService.findReviewByUserAndProduct(user, product).orElseThrow(()
                -> new ReviewNotExistingException("Вы еще  не писали отзыв на этот товар"));
        reviewService.deleteReview(existingReview);
        return mapper.getMapper().map(existingReview, ReviewResponse.class);

    }
    public void editExistingReview(Long productId, String comment, List<MultipartFile> images) {
        Product product = productService.findProductById(productId);
        User user = authService.getCurrentAuthorizedUser();
        Review existingReview = reviewService.findReviewByUserAndProduct(user, product).orElseThrow(()
                -> new ReviewNotExistingException("Вы еще  не писали отзыв на этот товар"));
        reviewService.deleteReview(existingReview);
        reviewService.saveReview(comment, images, product, user);
    }

    public void deleteReview(Long productId){
        Product product = productService.findProductById(productId);
        User user = authService.getCurrentAuthorizedUser();
        Review existingReview = reviewService.findReviewByUserAndProduct(user, product).orElseThrow(()
                -> new ReviewNotExistingException("Отзыв не найден"));
        reviewService.deleteReview(existingReview);
    }


    public void setRatingOnProduct(Long productId, Integer value){
        Product product = productService.findProductById(productId);
        User user = authService.getCurrentAuthorizedUser();
        ratingService.saveRating(user, product, value);
    }


    public void deleteRating(Long productId){
        Product product = productService.findProductById(productId);
        User user = authService.getCurrentAuthorizedUser();
        Rating currentRating = ratingService.findRatingByUserAndProduct(user, product);
        ratingService.deleteRating(currentRating);
    }
}
