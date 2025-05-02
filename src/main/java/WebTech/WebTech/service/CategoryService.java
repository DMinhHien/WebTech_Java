package WebTech.WebTech.service;
import WebTech.WebTech.domain.Category;
import WebTech.WebTech.domain.DTO.CategoryDTO;
import WebTech.WebTech.repository.CategoryRepository;


import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(long id) {
        Category category = this.categoryRepository.findById(id);
        if(category != null) {
            return category;
        }
        return null;
    }

    public List<Category> getCategoriesByIds(List<Long> ids) {
        return categoryRepository.findByIdIn(ids);
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
    public Category convertDTOtCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }

    public Category findByNameAndIdNot(String name, long id) {
        return categoryRepository.findByNameAndIdNot(name, id);
    }
    public void createCategory(CategoryDTO category) {
        Category categoryEntity = convertDTOtCategory(category);
        categoryRepository.save(categoryEntity);
    }
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
    public List<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }
    
}
