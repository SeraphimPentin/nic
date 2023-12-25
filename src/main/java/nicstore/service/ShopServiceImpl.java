package nicstore.service;

import lombok.RequiredArgsConstructor;
import nicstore.models.*;
import nicstore.dto.cart.ResponseOrder;
import nicstore.dto.cart.ResponseOrderList;
import nicstore.dto.cart.ResponseTotalOrder;
import nicstore.dto.mapper.ConvertorMapper;
import nicstore.dto.product.CartContentResponse;
import nicstore.dto.product.ProductCharacteristicsResponse;
import nicstore.dto.product.ProductResponse;
import nicstore.dto.product.ReviewResponse;
import nicstore.exceptions.NotEnoughProductsInStockException;
import nicstore.exceptions.ReviewNotExistingException;
import nicstore.service.interfaces.ShopService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final CategoryServiceImpl categoryService;
    private final AuthServiceImpl authService;
    private final ProductServiceImpl productService;
    private final RatingServiceImpl ratingService;
    private final ReviewServiceImpl reviewService;
    private final ConvertorMapper mapper;
    private final CartServiceImpl cartService;


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

    public void deleteReview(Long productId) {
        Product product = productService.findProductById(productId);
        User user = authService.getCurrentAuthorizedUser();
        Review existingReview = reviewService.findReviewByUserAndProduct(user, product).orElseThrow(()
                -> new ReviewNotExistingException("Отзыв не найден"));
        reviewService.deleteReview(existingReview);
    }


    public void setRatingOnProduct(Long productId, Integer value) {
        Product product = productService.findProductById(productId);
        User user = authService.getCurrentAuthorizedUser();
        ratingService.saveRating(user, product, value);
    }


    public void deleteRating(Long productId) {
        Product product = productService.findProductById(productId);
        User user = authService.getCurrentAuthorizedUser();
        Rating currentRating = ratingService.findRatingByUserAndProduct(user, product);
        ratingService.deleteRating(currentRating);
    }

    public ResponseOrder makeOrder() {
        CartContentResponse content = cartService.showCartContent();
        if (checkProductsAvailability(content)) {
            updatingQuantityProductsInStock(content);
            return sendOrderConfirmationEmail(content);
        } else {
            throw new NotEnoughProductsInStockException("Не достаточно товара на складе");
        }
    }


    public boolean checkProductsAvailability(CartContentResponse cartContent) {
        for (Map.Entry<ProductResponse, Integer> entry : cartContent.getItems().entrySet()) {
            ProductResponse product = entry.getKey();
            int cartQuantity = entry.getValue();
            if (cartQuantity > productService.findProductById(product.getId()).getQuantity()) {
                return false;
            }
        }
        return true;
    }

    public void updatingQuantityProductsInStock(CartContentResponse content) {
        for (Map.Entry<ProductResponse, Integer> entry : content.getItems().entrySet()) {
            ProductResponse product = entry.getKey();
            int cartQuantity = entry.getValue();
            int quantityProductInStock = product.getQuantity();
            int newQuantity = quantityProductInStock - cartQuantity;
            productService.updateProductQuantity(product.getId(), newQuantity);
        }
    }

    public ResponseTotalOrder createdOrderList(CartContentResponse content) {
        List<ResponseOrderList> orderList = new ArrayList<>();
        double totalCost = 0D;
        for (Map.Entry<ProductResponse, Integer> entry : content.getItems().entrySet()) {
            totalCost += entry.getValue() * entry.getKey().getPrice();
            ResponseOrderList listItem = ResponseOrderList.builder()
                    .name(entry.getKey().getName())
                    .quantity(entry.getValue())
                    .prise(entry.getKey().getPrice())
                    .build();
            orderList.add(listItem);
        }
        return ResponseTotalOrder.builder()
                .orderLists(orderList)
                .totalCost(totalCost)
                .build();
    }

    public ResponseOrder sendOrderConfirmationEmail(CartContentResponse cartContent) {
        String email = cartContent.getUserResponse().getEmail();
        ResponseTotalOrder order = createdOrderList(cartContent);
        String text = "Здравствуйте, " + cartContent.getUserResponse().getFirstname().toUpperCase() + "! " +
                "Ваш заказ: " + order.getOrderLists().toString() + ". Итоговая стоимость за все товары: " + order.getTotalCost() + " ₽." +
                " Ждем Вас еще!";
        return ResponseOrder.builder()
                .email(email)
                .answer(text)
                .build();
    }
}