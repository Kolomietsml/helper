package academy.productstore.service;

import academy.productstore.domain.Category;
import academy.productstore.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplUnitTest {

    @Mock
    private CategoryRepository mockRepository;

    @InjectMocks
    private CategoryServiceImpl service;

    @Test
    void getAll_shouldReturnsEmptyList() {
        // given

        // when
        List<Category> actual = service.getAll();

        // then
        assertTrue(actual.isEmpty());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    void getAll_shouldReturnsListWithCategories() {
        // given
        List<Category> categories = List.of(
                createTestCategory(1, "Beverages"),
                createTestCategory(2, "Fats and oils"));
        given(mockRepository.findAll()).willReturn(categories);

        // when
        List<Category> actual = service.getAll();

        // then
        assertEquals(2, actual.size());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    void getCategoryById_shouldThrowsEntityNotFoundException() {
        // given

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.getCategoryById(anyLong()));

        // then
        assertEquals("Category not found", exception.getMessage());
        verify(mockRepository, times(1)).findCategoryById(anyLong());
    }

    @Test
    void getCategoryById_shouldReturnsCategory() {
        // given
        var category = createTestCategory(1, "Beverages");
        given(mockRepository.findCategoryById(category.getId())).willReturn(category);

        // when
        Category actual = service.getCategoryById(category.getId());

        // then
        assertEquals(1, actual.getId());
        assertEquals("Beverages", actual.getName());
        verify(mockRepository, times(1)).findCategoryById(category.getId());
    }

    @Test
    void addCategory_shouldReturnsCategory() {
        // given
        var category = createTestCategory(1, "Beverages");
        given(mockRepository.save(any(Category.class))).willReturn(category);

        // when
        Category actual = service.addCategory(category);

        // then
        assertEquals(1, actual.getId());
        assertEquals("Beverages", actual.getName());
        verify(mockRepository, times(1)).save(any(Category.class));
    }

    @Test
    void updateCategory_shouldThrowsEntityNotFoundException() {
        // given
        var category = createTestCategory(1, "Beverages");

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.updateCategory(category, 2));

        // then
        assertEquals("Category not found", exception.getMessage());
        verify(mockRepository, times(1)).findCategoryById(2);
        verify(mockRepository, times(0)).save(any(Category.class));
    }

    @Test
    void updateCategory_shouldReturnsCategory() {
        // given
        var category = createTestCategory(1, "Beverages");
        given(mockRepository.findCategoryById(category.getId())).willReturn(category);
        given(mockRepository.save(any(Category.class))).willReturn(category);

        // when
        Category actual = service.updateCategory(category, category.getId());

        // then
        assertEquals(1, actual.getId());
        assertEquals("Beverages", actual.getName());
        verify(mockRepository, times(1)).findCategoryById(category.getId());
        verify(mockRepository, times(1)).save(any(Category.class));
    }

    @Test
    void deleteCategory_shouldThrowsEntityNotFoundException() {
        // given

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.deleteCategoryById(2));

        // then
        assertEquals("Category not found", exception.getMessage());
        verify(mockRepository, times(1)).findCategoryById(2);
        verify(mockRepository, times(0)).delete(any(Category.class));
    }

    @Test
    void deleteCategory() {
        // given
        var category = createTestCategory(1, "Beverages");
        given(mockRepository.findCategoryById(category.getId())).willReturn(category);

        // when
        service.deleteCategoryById(category.getId());

        // then
        verify(mockRepository, times(1)).findCategoryById(category.getId());
        verify(mockRepository, times(1)).delete(category);
    }

    private Category createTestCategory(long id, String name) {
        var category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
}