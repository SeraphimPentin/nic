package nicstore.service.impl;

import nicstore.Models.Category;
import nicstore.Models.Product;
import javax.transaction.Transactional;
import java.util.List;

public interface ProductServiceImpl {
    Product findProductById(Long id);

    List<Product> findProductsByCategory(Category category);
    @Transactional
    void saveProduct(Product product);
    @Transactional
    void deleteProduct(Product product);
}
