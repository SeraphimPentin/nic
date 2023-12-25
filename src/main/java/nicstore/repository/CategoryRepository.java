package nicstore.repository;

import nicstore.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByParentCategory(Category parentCategory);

    @Query("SELECT c FROM Category c WHERE c.id = :categoryId OR c.parentCategory.id = :categoryId")
    List<Category> findSubcategoriesByParentCategoryId(@Param("categoryId") Long categoryId);

}
