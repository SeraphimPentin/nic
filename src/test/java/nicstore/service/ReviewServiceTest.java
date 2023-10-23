package nicstore.service;

import nicstore.Models.*;

import nicstore.repository.ProductImageRepository;
import nicstore.repository.ReviewRepository;
import nicstore.utils.ImageAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @InjectMocks
    ReviewService reviewService;

    @Mock
    ReviewRepository reviewRepository;
    @Mock
    private ProductImageRepository imageRepository;
    @Mock
    private ImageAdapter imageAdapter;


    @Test
    void testFindReviewByUserAndProduct() {
        User user = new User();
        Product product = new Product();
       Optional<Review> expectedReview = Optional.of(new Review());
        when(reviewRepository.findReviewByUserAndProduct(user, product)).thenReturn(expectedReview);
        Optional<Review> actual = reviewService.findReviewByUserAndProduct(user, product);
        verify(reviewRepository).findReviewByUserAndProduct(user, product);
        assertEquals(expectedReview, actual);
    }

    @Test
    void testSaveReview() {
        User user = new User();
        Review review = Review.builder()
                .comment("comment")
                .user(user)
                .build();
        reviewRepository.save(review);
        verify(reviewRepository).save(review);
    }


    @Test
    void testDeleteReview() {
        Review review = new Review();
        review.setId(1L);
        review.setUser(new User());
        review.setComment("comm");
        review.setProduct(new Product());
        review.setImages(new ArrayList<>(Collections.singleton(new ProductImage())));
        reviewService.deleteReview(review);
        verify(imageRepository).deleteAll(review.getImages());
        String expectedPath =  "null/product" + review.getProduct().getId() + "/user" + review.getUser().getId();
        verify(imageAdapter).deleteFolder(expectedPath);
        verify(reviewRepository).delete(review);
    }
}