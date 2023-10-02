package nicstore.service;

import lombok.RequiredArgsConstructor;
import nicstore.Models.Category;
import nicstore.exceptions.CategoryNotFoundException;
import nicstore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Катеория не найдена"));
    }

    public List<Category> findSubcategoriesByParentCategory(Category parentCategory){
        return categoryRepository.findCategoriesByParentCategory(parentCategory);
    }

    public List<Category> findSubcategoriesByParentCategoryId(Long parentId){
        return categoryRepository.findSubcategoriesByParentCategoryId(parentId);
    }

    @Transactional
    public void saveCategory(Category category){
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Category category){
        categoryRepository.delete(category);
    }
 }
