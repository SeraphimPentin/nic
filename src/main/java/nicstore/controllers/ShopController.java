package nicstore.controllers;

import lombok.RequiredArgsConstructor;
import nicstore.dto.cart.ResponseOrder;
import nicstore.dto.product.*;

import nicstore.service.CartServiceImpl;
import nicstore.service.ShopServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/nic-shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopServiceImpl shopService;
    private final CartServiceImpl cartService;


    @PostMapping(path = "{productId}")
    public ResponseEntity<AddedProductToCartResponse> addProductToCart(@PathVariable("productId") Long productId) {
        cartService.addProductToCart(productId);
        return ResponseEntity.ok(new AddedProductToCartResponse(productId));
    }

    @PatchMapping
    public ResponseEntity<Void> changeProductQuantityInCart(Long productId, @RequestParam(name = "op") String operation) {
        cartService.changeQuantityProductInCart(productId, operation);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductCharacteristicsResponse> getProductPage(@PathVariable Long productId) {
        return ResponseEntity.ok(shopService.getProductPage(productId));
    }

    @PostMapping(value = "/{productId}/post-review", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<?> postReview(
            @PathVariable Long productId,
            @RequestParam(name = "comment") @Valid CommentRequest comment,
            @RequestParam(name = "files", required = false) List<MultipartFile> files
            ){
        shopService.setReviewOnProduct(productId, comment.getComment(), files);
        return ResponseEntity.ok("Отзыв добавлен");
    }

    @GetMapping("/{productId}/edit-review")
    public ResponseEntity<ReviewResponse> getReviewDTOForEditing(@PathVariable Long productId) {
        return ResponseEntity.ok(shopService.getReviewDTOForEditing(productId));
    }

    @PatchMapping(value = "/{productId}/edit-review", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<?> editReview(
            @RequestPart(name = "comment") @Valid CommentRequest comment,
            @RequestPart(name = "files", required = false) List<MultipartFile> files,
            @PathVariable Long productId
    ) {
        shopService.editExistingReview(productId, comment.getComment(), files);
        return ResponseEntity.ok("Ваш отзыв изменен!");
    }

    @DeleteMapping(value = "/{productId}/delete-rating", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<?> deleteRating(@PathVariable Long productId) {
        shopService.deleteRating(productId);
        return ResponseEntity.ok("Оценка удалена");
    }

    @DeleteMapping(value = "/{productId}/delete-review", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<?> deleteReview(@PathVariable Long productId) {
        shopService.deleteReview(productId);
        return ResponseEntity.ok("Отзыв удален");
    }

    @PostMapping(path = "{productId}/add-rating")
    public ResponseEntity<RatingResponse> addRatingToProduct(
            @PathVariable("productId") Long productId,
            @RequestParam(name = "value") Integer value
    ) {
        shopService.setRatingOnProduct(productId, value);
        return ResponseEntity.ok(new RatingResponse(productId, value));
    }

    @GetMapping(path = "order")
    public ResponseEntity<ResponseOrder> makeOrder() {
        return ResponseEntity.ok(shopService.makeOrder());
    }
}
