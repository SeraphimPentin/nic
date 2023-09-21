package nicstore.controllers;

import lombok.RequiredArgsConstructor;
import nicstore.dto.product.CartContentResponse;
import nicstore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;


    @GetMapping
    public ResponseEntity<CartContentResponse> getCartContent() {
        return ResponseEntity.ok(cartService.showCartContent());
    }

    @PatchMapping
    public ResponseEntity<Void> changeProductQuantityInCart(@RequestBody @Valid Long productId, @RequestParam(name = "op") String operation) {
        cartService.changeQuantityProductInCart(productId, operation);
        return ResponseEntity.ok().build();
    }
}
