package academy.productstore.service;

import academy.productstore.domain.Category;
import academy.productstore.domain.Product;
import academy.productstore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImpUnitTest {

    @Mock
    private ProductRepository mockRepository;

    @Mock
    private CategoryService mockCategoryService;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    void getProductsByCategory_shouldReturnsEmptyList() {
        // given
        Pageable pageable = PageRequest.of(0,20);
        Page<Product> products = new PageImpl<>(new ArrayList<>(), pageable, 0);
        given(mockRepository.findProductsByCategoryId(1, pageable)).willReturn(products);

        // when
        Page<Product> actual = service.getProductsByCategory(1, pageable);

        // then
        assertEquals(0, actual.getTotalElements());
        verify(mockRepository, times(1)).findProductsByCategoryId(1, pageable);
    }

    @Test
    void getProductsByCategory_shouldReturnsProductsList() {
        // given
        var category = createTestCategory(1L, "Beverages");
        List<Product> list = List.of(
                createTestProduct(2, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5), category),
                createTestProduct(3, "Pepsi", "", BigDecimal.valueOf(0.45), category)
        );
        Pageable pageable = PageRequest.of(0,20);
        Page<Product> products = new PageImpl<>(list, pageable, 1);
        given(mockRepository.findProductsByCategoryId(1, pageable)).willReturn(products);

        // when
        Page<Product> actual = service.getProductsByCategory(1, pageable);

        // then
        assertEquals(2, actual.getTotalElements());
        assertEquals(1, actual.getContent().get(0).getCategory().getId());
        verify(mockRepository, times(1)).findProductsByCategoryId(1, pageable);
    }

    @Test
    void search_shouldReturnsProductsList() {
        // given
        var category = createTestCategory(1L, "Beverages");
        List<Product> list = List.of(
                createTestProduct(2, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5), category));
        Pageable pageable = PageRequest.of(0,20);
        Page<Product> products = new PageImpl<>(list, pageable, 1);
        given(mockRepository.search("Co", pageable)).willReturn(products);

        // when
        Page<Product> actual = service.search("Co" ,pageable);

        //then
        assertEquals(1, actual.getTotalElements());
        assertEquals(1, actual.getContent().get(0).getCategory().getId());
        verify(mockRepository, times(1)).search("Co", pageable);
    }

    @Test()
    void getProductById_shouldThrowsEntityNotFoundException() {
        // given

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.getProductById(anyLong()));

        // then
        assertEquals("Product not found", exception.getMessage());
        verify(mockRepository, times(1)).findProductById(anyLong());
    }

    @Test()
    void getProductById_shouldReturnsProduct() {
        // given
        var category = createTestCategory(1L, "Beverages");
        var product = createTestProduct(2, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5), category);
        given(mockRepository.findProductById(product.getId())).willReturn(product);

        // when
        Product actual = service.getProductById(product.getId());

        // then
        assertEquals(2, actual.getId());
        assertEquals("Coca-Cola", actual.getName());
        assertEquals("Beverages", actual.getCategory().getName());
        verify(mockRepository, times(1)).findProductById(product.getId());
    }

    @Test
    void addProduct_shouldReturnsProduct() {
        // given
        var product = createTestProduct(2, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5), null);
        given(mockRepository.save(any(Product.class))).willReturn(product);

        // when
        Product actual = service.addProduct(product);

        // then
        assertEquals(2, actual.getId());
        assertEquals("Coca-Cola", actual.getName());
        assertEquals(BigDecimal.valueOf(0.5), actual.getPrice());
        verify(mockRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldReturnsProduct() {
        // given
        var product = createTestProduct(1, "Pepsi", "Diet", BigDecimal.valueOf(0.5), null);
        given(mockRepository.findProductById(product.getId())).willReturn(product);
        given(mockRepository.save(any(Product.class))).willReturn(product);

        // when
        Product actual = service.updateProduct(product, product.getId());

        // then
        assertEquals(1, actual.getId());
        assertEquals("Pepsi", actual.getName());
        assertEquals(BigDecimal.valueOf(0.5), actual.getPrice());
        verify(mockRepository, times(1)).findProductById(product.getId());
        verify(mockRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldThrowsEntityNotFoundException() {
        // given
        var product = createTestProduct(2, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5), null);

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.updateProduct(product, 1));

        // then
        assertEquals("Product not found", exception.getMessage());
        verify(mockRepository, times(1)).findProductById(1);
        verify(mockRepository, times(0)).save(any(Product.class));
    }

    @Test
    void deleteProduct() {
        // given
        var product = createTestProduct(2, "Coca-Cola", "", BigDecimal.valueOf(0.5), null);
        given(mockRepository.findProductById(product.getId())).willReturn(product);

        // when
        service.deleteProductById(product.getId());

        // then
        verify(mockRepository, times(1)).findProductById(product.getId());
        verify(mockRepository, times(1)).delete(product);
    }

    @Test
    void deleteProduct_shouldThrowsEntityNotFoundException() {
        // given

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.deleteProductById(1));

        // then
        assertEquals("Product not found", exception.getMessage());
        verify(mockRepository, times(1)).findProductById(1);
        verify(mockRepository, times(0)).delete(any(Product.class));
    }

    @Test
    void addProductToCategory_WhenProductIsNotFound_shouldThrowsEntityNotFoundException() {
        // given

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.addProductToCategory(1, 2));

        // then
        assertEquals("Product not found", exception.getMessage());
        verify(mockRepository, times(1)).findProductById(1);
        verify(mockCategoryService, times(0)).getCategoryById(1);
        verify(mockRepository, times(0)).save(any(Product.class));
    }

    @Test
    void addProductToCategory_WhenCategoryIsNotFound_shouldThrowsEntityNotFoundException() {
        // given
        var product = createTestProduct(1, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5), null);
        given(mockRepository.findProductById(product.getId())).willReturn(product);
        given(mockCategoryService.getCategoryById(2)).willThrow(new EntityNotFoundException("Category not found"));

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.addProductToCategory(product.getId(), 2));

        // then
        assertEquals("Category not found", exception.getMessage());
        verify(mockRepository, times(1)).findProductById(1);
        verify(mockCategoryService, times(1)).getCategoryById(2);
        verify(mockRepository, times(0)).save(any(Product.class));
    }

    @Test
    void addProductToCategory_shouldReturnsProductWithUpdatedCategory() {
        // given
        var category = createTestCategory(2L, "Beverages");
        var product = createTestProduct(1, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5), null);
        var expected = createTestProduct(1, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5), category);
        given(mockRepository.findProductById(product.getId())).willReturn(product);
        given(mockCategoryService.getCategoryById(category.getId())).willReturn(category);
        given(mockRepository.save(any(Product.class))).willReturn(expected);

        // when
        var actual = service.addProductToCategory(product.getId(), category.getId());

        // then
        assertEquals(1, actual.getId());
        assertEquals("Coca-Cola", actual.getName());
        assertEquals(BigDecimal.valueOf(0.5), actual.getPrice());
        assertEquals(2, actual.getCategory().getId());
        assertEquals("Beverages", actual.getCategory().getName());
        verify(mockRepository, times(1)).findProductById(1);
        verify(mockCategoryService, times(1)).getCategoryById(2);
        verify(mockRepository, times(1)).save(any(Product.class));
    }

    @Test
    void addProductToCategory_shouldReturnsProductWithNotUpdatedCategory() {
        // given
        var category = createTestCategory(2L, "Dairy products");
        var product = createTestProduct(1, "Milk", "1L, 3.2%", BigDecimal.valueOf(0.5), category);
        given(mockRepository.findProductById(product.getId())).willReturn(product);
        given(mockCategoryService.getCategoryById(category.getId())).willReturn(category);

        // when
        var actual = service.addProductToCategory(product.getId(), category.getId());

        // then
        assertEquals(1, actual.getId());
        assertEquals("Milk", actual.getName());
        assertEquals(BigDecimal.valueOf(0.5), actual.getPrice());
        assertEquals(2, actual.getCategory().getId());
        assertEquals("Dairy products", actual.getCategory().getName());
        verify(mockRepository, times(1)).findProductById(1);
        verify(mockCategoryService, times(1)).getCategoryById(2);
        verify(mockRepository, times(0)).save(any(Product.class));
    }

    @Test
    void removeProductFromCategory_WhenProductDoesNotExist_shouldThrowsEntityNotFoundException() {
        // given

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.removeProductFromCategory(1, 2));

        // then
        assertEquals("Product not found", exception.getMessage());
        verify(mockRepository, times(1)).findProductById(1);
        verify(mockCategoryService, times(0)).getCategoryById(1);
        verify(mockRepository, times(0)).save(any(Product.class));
    }

    @Test
    void removeProductFromCategory_WhenCategoryDoesNotExist_shouldThrowsEntityNotFoundException() {
        // given
        var product = createTestProduct(1, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5), null);
        given(mockRepository.findProductById(product.getId())).willReturn(product);
        given(mockCategoryService.getCategoryById(2)).willThrow(new EntityNotFoundException("Category not found"));

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.removeProductFromCategory(product.getId(), 2));

        // then
        assertEquals("Category not found", exception.getMessage());
        verify(mockRepository, times(1)).findProductById(1);
        verify(mockCategoryService, times(1)).getCategoryById(2);
        verify(mockRepository, times(0)).save(any(Product.class));
    }

    private Product createTestProduct(long id, String name, String desc, BigDecimal price, Category category) {
        var product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(desc);
        product.setPrice(price);
        product.setCategory(category);
        return product;
    }

    private Category createTestCategory(long id, String name) {
        var category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
}