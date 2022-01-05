package academy.productstore.service;

import academy.productstore.persistence.entity.Category;
import academy.productstore.persistence.entity.Product;
import academy.productstore.persistence.repository.CategoryRepository;
import academy.productstore.persistence.repository.ProductRepository;
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
import java.util.Optional;

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
    private CategoryRepository mockCategoryRepository;

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
    void getProductsByCategory() {
        // given
        List<Product> list = List.of(
                createTestProduct(2, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5)),
                createTestProduct(3, "Pepsi", "", BigDecimal.valueOf(0.45)));
        Pageable pageable = PageRequest.of(0,20);
        Page<Product> products = new PageImpl<>(list, pageable, 1);
        given(mockRepository.findProductsByCategoryId(1, pageable)).willReturn(products);

        // when
        Page<Product> actual = service.getProductsByCategory(1, pageable);

        // then
        assertEquals(2, actual.getTotalElements());
        verify(mockRepository, times(1)).findProductsByCategoryId(1, pageable);
    }

    @Test
    void search() {
        // given
        List<Product> list = List.of(
                createTestProduct(2, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5)));
        Pageable pageable = PageRequest.of(0,20);
        Page<Product> products = new PageImpl<>(list, pageable, 1);
        given(mockRepository.search("Co", pageable)).willReturn(products);

        // when
        Page<Product> actual = service.search("Co" ,pageable);

        //then
        assertEquals(1, actual.getTotalElements());
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
        var product = createTestProduct(2, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5));
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
        var product = createTestProduct(2, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5));
        given(mockRepository.save(any(Product.class))).willReturn(product);

        // when
        Product actual = service.addProduct(product);

        // then
        assertEquals(2, actual.getId());
        assertEquals("Coca-Cola", actual.getName());
        assertEquals(BigDecimal.valueOf(0.5), actual.getPrice());
        assertEquals(1, actual.getCategory().getId());
        verify(mockCategoryRepository, times(1)).findById(product.getCategory().getId());
        verify(mockRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldReturnsProduct() {
        // given
        var product = createTestProduct(2, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5));
        given(mockRepository.findProductById(product.getId())).willReturn(product);
        given(mockCategoryRepository.findById(product.getCategory().getId())).willReturn(Optional.of(product.getCategory()));
        given(mockRepository.save(any(Product.class))).willReturn(product);

        // when
        Product actual = service.updateProduct(product, product.getId());

        // then
        assertEquals(2, actual.getId());
        assertEquals("Coca-Cola", actual.getName());
        assertEquals(BigDecimal.valueOf(0.5), actual.getPrice());
        assertEquals(1, actual.getCategory().getId());
        verify(mockRepository, times(1)).findProductById(product.getId());
        verify(mockCategoryRepository, times(1)).findById(product.getCategory().getId());
        verify(mockRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldThrowsEntityNotFoundException() {
        // given
        var product = createTestProduct(2, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5));

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.updateProduct(product, 1));

        // then
        assertEquals("Product not found", exception.getMessage());
        verify(mockRepository, times(1)).findProductById(1);
        verify(mockCategoryRepository, times(0)).findById(product.getId());
        verify(mockRepository, times(0)).save(any(Product.class));
    }

    @Test
    void deleteProduct() {
        // given
        var product = createTestProduct(2, "Coca-Cola", "Diet", BigDecimal.valueOf(0.5));
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

    private Product createTestProduct(long id, String name, String desc, BigDecimal price) {
        var category = new Category();
        category.setId(1L);
        category.setName("Beverages");

        var product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(desc);
        product.setPrice(price);
        product.setCategory(category);

        return product;
    }
}