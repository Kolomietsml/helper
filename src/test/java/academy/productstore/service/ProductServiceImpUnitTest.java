package academy.productstore.service;

import academy.productstore.persistence.entity.Category;
import academy.productstore.persistence.entity.Product;
import academy.productstore.persistence.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImpUnitTest {

    @Mock
    private ProductRepository mockRepository;

    @Mock
    private CategoryService mockService;

    @InjectMocks
    private ProductServiceImpl service;

    private Product product;
    private Category category;
    private Page<Product> products;
    private final Pageable pageable = PageRequest.of(0,20);

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Beverages");

        product = new Product();
        product.setId(2L);
        product.setName("Coca-Cola");
        product.setDescription("");
        product.setPrice(BigDecimal.valueOf(0.5));
        product.setCategory(category);

        products = new PageImpl<>(List.of(product), pageable, 1);
    }

    @AfterEach
    void tearDown() {
        category = null;
        product = null;
        products = null;
    }

    @Test
    void getProductsByCategory_shouldReturnEmptyList() {
        // given
        long id = 2;
        Page<Product> pages = new PageImpl<>(new ArrayList<>(), pageable, 0);
        given(mockRepository.findProductsByCategoryId(id, pageable)).willReturn(pages);

        // when
        Page<Product> actual = service.getProductsByCategory(id, pageable);

        // then
        assertEquals(0, actual.getTotalElements());
        verify(mockRepository, times(1)).findProductsByCategoryId(id, pageable);
    }

    @Test
    void getProductsByCategory() {
        // given
        long id = 1;
        given(mockRepository.findProductsByCategoryId(id, pageable)).willReturn(products);

        // when
        Page<Product> actual = service.getProductsByCategory(id, pageable);

        // then
        assertEquals(1, actual.getTotalElements());
        verify(mockRepository, times(1)).findProductsByCategoryId(id, pageable);
    }

    @Test
    void search() {
        // given
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
    void getProductById_shouldReturnProduct() {
        // given
        long id = 2;
        given(mockRepository.findProductById(id)).willReturn(product);

        // when
        Product actual = service.getProductById(id);

        // then
        assertEquals(2, actual.getId());
        assertEquals("Coca-Cola", actual.getName());
        assertEquals("Beverages", actual.getCategory().getName());
        verify(mockRepository, times(1)).findProductById(id);
    }

    @Test
    void addProduct_shouldReturnProduct() {
        // given
        given(mockService.getCategoryById(1)).willReturn(category);
        given(mockRepository.save(any(Product.class))).willReturn(product);

        // when
        Product actual = service.addProduct(product);

        // then
        assertEquals(2, actual.getId());
        assertEquals("Coca-Cola", actual.getName());
        assertEquals(BigDecimal.valueOf(0.5), actual.getPrice());
        assertEquals(category.getId(), actual.getCategory().getId());
        verify(mockService, times(1)).getCategoryById(1);
        verify(mockRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldReturnProduct() {
        // given
        long id = 2;
        given(mockService.getCategoryById(1)).willReturn(category);
        given(mockRepository.findProductById(id)).willReturn(product);
        given(mockRepository.save(any(Product.class))).willReturn(product);

        // when
        Product actual = service.updateProduct(product, id);

        // then
        assertEquals(2, actual.getId());
        assertEquals("Coca-Cola", actual.getName());
        assertEquals(BigDecimal.valueOf(0.5), actual.getPrice());
        assertEquals(category.getId(), actual.getCategory().getId());
        verify(mockRepository, times(1)).findProductById(id);
        verify(mockService, times(1)).getCategoryById(1);
        verify(mockRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldThrowsEntityNotFoundException() {
        // given
        long id = anyLong();

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.updateProduct(product, id));

        // then
        assertEquals("Product not found", exception.getMessage());
        verify(mockRepository, times(1)).findProductById(id);
        verify(mockService, times(0)).getCategoryById(1);
        verify(mockRepository, times(0)).save(any(Product.class));
    }

    @Test
    void deleteProduct() {
        // given
        given(mockRepository.findProductById(1)).willReturn(product);

        // when
        service.deleteProductById(1);

        // then
        verify(mockRepository, times(1)).findProductById(1);
        verify(mockRepository, times(1)).delete(product);
    }

    @Test
    void deleteProduct_shouldThrowsEntityNotFoundException() {
        // given
        long id = anyLong();

        // when
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> service.deleteProductById(id));

        // then
        assertEquals("Product not found", exception.getMessage());
        verify(mockRepository, times(1)).findProductById(id);
        verify(mockRepository, times(0)).delete(any(Product.class));
    }
}