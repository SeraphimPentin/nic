package nicstore.controllers;

import lombok.RequiredArgsConstructor;
import nicstore.dto.product.CartContentResponse;

import nicstore.service.CartServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {


    private final CartServiceImpl cartService;


    @GetMapping
    public ResponseEntity<CartContentResponse> getCartContent() {
        return ResponseEntity.ok(cartService.showCartContent());
    }
}
