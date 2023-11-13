package nicstore.service.interfaces;

import nicstore.Models.Category;
import javax.transaction.Transactional;
import java.util.List;

public interface CategoryService {
    Category findCategoryById(Long id);

    List<Category> findSubcategoriesByParentCategory(Category parentCategory);

    List<Category> findSubcategoriesByParentCategoryId(Long parentId);

    @Transactional
    void saveCategory(Category category);

    @Transactional
    void deleteCategory(Category category);
}
