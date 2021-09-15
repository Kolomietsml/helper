package academy.Motorola.service;

import academy.Motorola.entity.Category;
import academy.Motorola.repository.CategoryRepository;
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
    public void addCategory(Category category) {
        var c = new Category();
        c.setName(category.getName());
        categoryRepository.save(c);
    }

    @Override
    public void updateCategory(Category category, long id) {
        var c = getCategoryById(id);
        c.setName(category.getName());
        categoryRepository.save(c);
    }

    @Override
    public void deleteCategoryById(long id) {
        var category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}
