package nicstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CategoryService categoryService;
    private final AuthService authService;
    private final ProductService productService;
    private final RatingService ratingService;
    private final ReviewService reviewService;

    private final Map<String, List<String>> categoriesCache = new HashMap<>();


}
