package nicstore.service;

import nicstore.StoreApplication;
import nicstore.dto.auth.UserResponse;
import nicstore.dto.cart.ResponseOrder;
import nicstore.dto.product.CartContentResponse;
import nicstore.dto.product.ProductCharacteristicsResponse;
import nicstore.dto.product.ProductResponse;
import nicstore.exceptions.EmptyCartException;
import nicstore.exceptions.ProductNotFoundException;
import nicstore.exceptions.ReviewNotExistingException;
import nicstore.models.*;
import nicstore.repository.ProductRepository;
import nicstore.repository.RatingRepository;
import nicstore.repository.ReviewRepository;
import nicstore.service.interfaces.RatingService;
import nicstore.service.interfaces.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = StoreApplication.class)
@TestPropertySource(
        locations = "classpath:application-integration.properties")
public class ShopServiceIntegrationTest {

    private final ShopService shopService;
    private final ProductRepository productRepository;
    private final ReviewServiceImpl reviewService;
    private final ReviewRepository reviewRepository;
    private final RatingService ratingService;
    private final RatingRepository ratingRepository;
    private final UserServiceImpl userService;
    private final AuthServiceImpl authService;
    private User user;
    private Product product;

    @Autowired
    public ShopServiceIntegrationTest(ShopService shopService,
                                      ProductRepository productRepository,
                                      ReviewServiceImpl reviewService,
                                      ReviewRepository reviewRepository,
                                      RatingService ratingService,
                                      RatingRepository ratingRepository,
                                      UserServiceImpl userService,
                                      AuthServiceImpl authService) {
        this.shopService = shopService;
        this.productRepository = productRepository;
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
        this.ratingService = ratingService;
        this.ratingRepository = ratingRepository;
        this.userService = userService;
        this.authService = authService;
    }

    @BeforeEach
    void setUp() {
        product = productRepository.save(Product.builder()
                .name("Product name")
                .description("Description")
                .price(BigDecimal.valueOf(25))
                .quantity(4)
                .images(Collections.emptyList())
                .categories(new HashSet<>())
                .build());

        user = User.builder()
                .firstname("Bob")
                .lastname("Bobichev")
                .password("Password")
                .email("bobik@mail.com")
                .build();

        userService.saveUser(user);
        user = userService.findUserByEmail("bobik@mail.com");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        user = authService.getCurrentAuthorizedUser();
    }

    @Test
    void testGetProductPage() {

        ProductCharacteristicsResponse productPage = shopService.getProductPage(product.getId());

        assertThat(productPage.getName()).isEqualTo(product.getName());
        assertThat(productPage.getImages()).isEmpty();
        assertThat(productPage.getDescription()).isEqualTo(product.getDescription());
        assertThat(productPage.getQuantity()).isEqualTo(product.getQuantity());
        assertThat(productPage.getNumberRatings()).isZero();
        assertThat(productPage.getNumberReviews()).isZero();
        assertThat(productPage.getReviews()).isEmpty();
    }


    @Test
    void testSetReviewOnProduct() {
        // создаем тестовый отзыв
        List<MultipartFile> images = new ArrayList<>();
        String comment = "Test comment";

        shopService.setReviewOnProduct(product.getId(), comment, images);

        // проверяем, что отзыв был сохранен в базе данных
        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(reviews.size());
        Review review = reviewService.findReviewByUserAndProduct(user, product).get();

        assertThat(review.getUser().getId()).isEqualTo(user.getId());
        assertThat(review.getProduct().getId()).isEqualTo(product.getId());
        assertThat(review.getComment()).isEqualTo(comment);
    }

    @Test
    void testGetReviewDTOForEditing_ReviewNotExistingException() {
        assertThrows(ReviewNotExistingException.class, () -> shopService.getReviewDTOForEditing(product.getId()));
    }

    @Test
    void testSetRatingOnProduct() throws ProductNotFoundException {
        shopService.setRatingOnProduct(product.getId(), 5);

        List<Rating> ratings = ratingRepository.findAll();
        assertThat(ratings).hasSize(1);
        Rating rating = ratings.get(0);
        assertThat(rating.getProduct().getId()).isEqualTo(product.getId());
        assertThat(rating.getUser().getId()).isEqualTo(user.getId());
        assertThat(rating.getValue()).isEqualTo(5);
    }

    @Test
    void testDeleteRating() {
        ratingService.saveRating(user, product, 5);
        shopService.deleteRating(product.getId());

        assertThat(ratingRepository.findAll()).isEmpty();
    }

    @Test
    void testCheckProductsAvailability_EnoughQuantity() {
        // Формируем корзину с достаточным количеством товара
        Map<ProductResponse, Integer> items = new HashMap<>();
        items.put(new ProductResponse(product.getId(), product.getName(), "", 4, 25.0), 4);
        CartContentResponse cartContent = new CartContentResponse(
                new UserResponse(
                        user.getEmail(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getRole()),
                items);

        // Проверяем, что метод возвращает true, когда достаточное количество товаров в наличии
        assertTrue(shopService.checkProductsAvailability(cartContent));
    }

    @Test
    void testCheckProductsAvailability_NotEnoughQuantity() {
        // Формируем корзину с недостаточным количеством товара
        Map<ProductResponse, Integer> items = new HashMap<>();
        items.put(new ProductResponse(product.getId(), product.getName(), "", 4, 25.0), 6);
        CartContentResponse cartContent = new CartContentResponse(
                new UserResponse(
                        user.getEmail(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getRole()),
                items);
        // Проверяем, что метод возвращает false, когда недостаточное количество товаров в наличии
        assertFalse(shopService.checkProductsAvailability(cartContent));
    }

    @Test
    void testSendOrderConfirmationEmail() {
        Map<ProductResponse, Integer> items = new HashMap<>();
        items.put(ProductResponse.builder()
                .id(1L)
                .name("Product A")
                .price(10.0)
                .build(), 2);
        items.put(ProductResponse.builder()
                .id(2L)
                .name("Product B")
                .price(20.0)
                .build(), 1);

        CartContentResponse cartContent = new CartContentResponse(new UserResponse(
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getRole()), items);
        ResponseOrder responseOrder = shopService.sendOrderConfirmationEmail(cartContent);
        assertEquals(user.getEmail(), responseOrder.getEmail());
    }

    @Test
    void testMakeOrder_EmptyCart() {
        // Проверяем, что метод выбрасывает исключение, если корзина пуста
        assertThrows(EmptyCartException.class, shopService::makeOrder);
    }
}
