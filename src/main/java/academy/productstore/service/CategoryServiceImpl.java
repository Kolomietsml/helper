package academy.productstore.service;

import academy.productstore.persistence.entity.Category;
import academy.productstore.persistence.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(long id) {
        var category = categoryRepository.findCategoryById(id);
        if (category == null) {
            throw new EntityNotFoundException("Category not found");
        }
        return category;
    }

    @Override
    public Category addCategory(Category category) {
        var c = new Category();
        c.setName(category.getName());
        return categoryRepository.save(c);
    }

    @Override
    public Category updateCategory(Category category, long id) {
        var c = getCategoryById(id);
        c.setName(category.getName());
        return categoryRepository.save(c);
    }

    @Override
    public void deleteCategoryById(long id) {
        var category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}
