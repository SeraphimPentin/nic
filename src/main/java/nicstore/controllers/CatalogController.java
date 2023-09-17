package nicstore.controllers;

import lombok.RequiredArgsConstructor;
import nicstore.service.CartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

//    private final CatalogService catalogService;
    private final CartService cartService;
}
