package nicstore.repository;


import nicstore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "WITH RECURSIVE subcategories(id) AS (SELECT id FROM categories WHERE id = ?1 UNION " +
            "SELECT c.id FROM categories c JOIN subcategories sc ON c.parent_category_id = sc.id) " +
            "SELECT p.* FROM products p JOIN products_categories pc ON p.id = pc.product_id JOIN subcategories s ON pc.category_id = s.id",
            nativeQuery = true)
    List<Product> findProductsByCategoryId(@Param("categoryId") Long category_id);

}