package nicstore.service.impl;

import nicstore.Models.Category;
import javax.transaction.Transactional;
import java.util.List;

public interface CategoryServiceImpl {
    Category findCategoryById(Long id);

    List<Category> findSubcategoriesByParentCategory(Category parentCategory);

    List<Category> findSubcategoriesByParentCategoryId(Long parentId);

    @Transactional
    void saveCategory(Category category);

    @Transactional
    void deleteCategory(Category category);
}
