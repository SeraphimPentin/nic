package nicstore.service.interfaces;

import nicstore.Models.Category;
import nicstore.Models.Product;
import javax.transaction.Transactional;
import java.util.List;

public interface ProductService {
    Product findProductById(Long id);

    List<Product> findProductsByCategory(Category category);
    @Transactional
    void saveProduct(Product product);
    @Transactional
    void deleteProduct(Product product);
}
