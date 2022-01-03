package academy.productstore.service;

import academy.productstore.persistence.entity.Category;
import academy.productstore.persistence.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    private Category category1;
    private Category category2;
    private List<Category> categories;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setId(1L);
        category1.setName("Beverages");

        category2 = new Category();
        category2.setId(2L);
        category2.setName("Fats and oils");

        categories = List.of(category1, category2);
    }

    @AfterEach
    void tearDown() {
        category1 = null;
        category2 = null;
        categories = null;
    }

    @Test
    void getAll_shouldReturnEmptyList() {
        // given

        // when
        List<Category> actual = service.getAll();

        // then
        assertTrue(actual.isEmpty());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    void getAll_shouldReturnListWithCategories() {
        // given
        given(mockRepository.findAll()).willReturn(categories);

        // when
        List<Category> actual = service.getAll();

        // then
        assertEquals(2, actual.size());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    void getCategoryById_shouldThrowEntityNotFoundException() {
        // given

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.getCategoryById(anyLong()));

        // then
        assertEquals("Category not found", exception.getMessage());
        verify(mockRepository, times(1)).findCategoryById(anyLong());
    }

    @Test
    void getCategoryById_shouldReturnCategory() {
        // given
        given(mockRepository.findCategoryById(1)).willReturn(category1);

        // when
        Category actual = service.getCategoryById(1);

        // then
        assertEquals(1, actual.getId());
        assertEquals("Beverages", actual.getName());
        verify(mockRepository, times(1)).findCategoryById(1);
    }

    @Test
    void addCategory_shouldReturnCategory() {
        // given
        given(mockRepository.save(any(Category.class))).willReturn(category1);

        // when
        Category actual = service.addCategory(category1);

        // then
        assertEquals(1, actual.getId());
        assertEquals("Beverages", actual.getName());
        verify(mockRepository, times(1)).save(any(Category.class));
    }

    @Test
    void updateCategory_shouldThrowEntityNotFoundException() {
        // given
        long id = anyLong();

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.updateCategory(category1, id));

        // then
        assertEquals("Category not found", exception.getMessage());
        verify(mockRepository, times(1)).findCategoryById(id);
        verify(mockRepository, times(0)).save(any(Category.class));
    }

    @Test
    void updateCategory_shouldReturnCategory() {
        // given
        given(mockRepository.findCategoryById(1)).willReturn(category1);
        given(mockRepository.save(any(Category.class))).willReturn(category1);

        // when
        Category actual = service.updateCategory(category1, 1);

        // then
        assertEquals(1, actual.getId());
        assertEquals("Beverages", actual.getName());
        verify(mockRepository, times(1)).findCategoryById(1);
        verify(mockRepository, times(1)).save(any(Category.class));
    }

    @Test
    void deleteCategory_shouldThrowEntityNotFoundException() {
        // given
        long id = anyLong();

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.deleteCategoryById(id));

        // then
        assertEquals("Category not found", exception.getMessage());
        verify(mockRepository, times(1)).findCategoryById(id);
        verify(mockRepository, times(0)).delete(any(Category.class));
    }

    @Test
    void deleteCategory() {
        // given
        given(mockRepository.findCategoryById(1)).willReturn(category1);

        // when
        service.deleteCategoryById(1);

        // then
        verify(mockRepository, times(1)).findCategoryById(1);
        verify(mockRepository, times(1)).delete(category1);
    }
}