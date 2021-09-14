package academy.Motorola.service;

import academy.Motorola.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();
    Category getCategoryById(long id);

    void addCategory(Category category);
    void updateCategory(Category category, long id);
    void deleteCategoryById(long id);
}
