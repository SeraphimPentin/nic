package nicstore.service;

import lombok.RequiredArgsConstructor;
import nicstore.Models.Product;
import nicstore.Models.ProductImage;
import nicstore.Models.Review;
import nicstore.Models.User;
import nicstore.repository.ProductImageRepository;
import nicstore.repository.ReviewRepository;
import nicstore.utils.ImageAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    @Value("${path_for_review_image}")
    private String PATH_FOR_REVIEW_IMAGE;

    private final ReviewRepository reviewRepository;
    private final ProductImageRepository imageRepository;
    private final ImageAdapter imageAdapter;


    public Optional<Review> findReviewById(Long id){
        return reviewRepository.findById(id);
    }

    public List<Review> findReviewsByProduct(Product product){
        return reviewRepository.findReviewsByProduct(product);
    }

    public Optional<Review> findReviewByUserAndProduct(User user, Product product){
        return reviewRepository.findReviewByUserAndProduct(user, product);
    }

    @Transactional
    public void saveReview(String comment, List<MultipartFile> files, Product product, User user){
        reviewRepository.save(
                Review.builder()
                        .comment(comment)
                        .createdAt(LocalDateTime.now())
                        .product(product)
                        .images(saveReviewImage(files, product.getId(), user.getId()))
                        .user(user)
                        .build()
        );
    }

    @Transactional
    public List<ProductImage> saveReviewImage(List<MultipartFile> files, Long productId, Long userId) {
        int i = 0;
        List<ProductImage> savedImages = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            String upperFolderPath = PATH_FOR_REVIEW_IMAGE;
            String productIdPath = "/product" + productId;
            String userFolderName = "/user" + userId;
            String finalPath = upperFolderPath + File.separator + productIdPath + File.separator + userFolderName;

            for (MultipartFile file : files) {
                String fileName = "review_image_" + i + "." + Objects.requireNonNull(file.getContentType()).substring(6);
                imageAdapter.saveImage(file, finalPath, fileName);
                ProductImage image = ProductImage.builder()
                        .name(fileName)
                        .type(file.getContentType())
                        .path(finalPath)
                        .build();
                savedImages.add(image);
                imageRepository.save(image);
                i++;
            }
        }
        return savedImages;
    }


    @Transactional
    public void deleteReview(Review review) {
        imageRepository.deleteAll(review.getImages());
        imageAdapter.deleteFolder(PATH_FOR_REVIEW_IMAGE + "/product" + review.getProduct().getId() + "/user" + review.getUser().getId());
        reviewRepository.delete(review);
    }
}
