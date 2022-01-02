package academy.productstore.service;

import academy.productstore.persistence.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();
    Category getCategoryById(long id);

    Category addCategory(Category category);
    Category updateCategory(Category category, long id);
    void deleteCategoryById(long id);

    void deleteAll();
}
