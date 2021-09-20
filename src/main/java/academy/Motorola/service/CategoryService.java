package academy.Motorola.service;

import academy.Motorola.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();
    Category getCategoryById(long id);

    Category addCategory(Category category);
    Category updateCategory(Category category, long id);
    void deleteCategoryById(long id);
}
