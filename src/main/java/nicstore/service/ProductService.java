package nicstore.service;

import lombok.RequiredArgsConstructor;
import nicstore.Models.Category;
import nicstore.Models.Product;
import nicstore.exceptions.ProductNotFoundException;
import nicstore.repository.ProductImageRepository;
import nicstore.repository.ProductRepository;
import nicstore.utils.ImageAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {


    @Value("${path_for_review_image}")
    private String PATH_FOR_REVIEW_IMAGE;

    @Value("${path_for_product_image}")
    private String PATH_FOR_PRODUCT_IMAGE;


    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ImageAdapter imageAdapter;
    private ReviewService reviewService;
    private RatingService ratingService;

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Товар не найден"));
    }

    public List<Product> findProductsByCategory(Category category) {
        return productRepository.findProductsByCategoryId(category.getId());
    }

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    @Transactional
    public void deleteProduct(Product product) {
        productImageRepository.deleteAll(product.getImages());
        imageAdapter.deleteFolder(PATH_FOR_PRODUCT_IMAGE + "/" + product.getId());
        product.clearCategories();
        reviewService.findReviewsByProduct(product).forEach(review -> reviewService.deleteReview(review));// reviewService::deleteReview
        imageAdapter.deleteFolder(PATH_FOR_REVIEW_IMAGE + "/product" + product.getId());
        ratingService.findRatingsByProduct(product).forEach(rating -> ratingService.deleteRating(rating)); //ratingService::deleteRating
        productRepository.delete(product);
    }

}
