package academy.productstore.service;

import academy.productstore.entity.Category;
import academy.productstore.entity.Product;
import academy.productstore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ProductServiceImpUnitTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final CategoryService categoryService = mock(CategoryService.class);

    private final ProductService productService = new ProductServiceImpl(productRepository, categoryService);

    @Test
    void getAll_shouldReturnEmptyList() {
        // given

        // when
        List<Product> expected = productService.getAll();

        // then
        assertThat(expected).isEmpty();
    }

//    @Test
//    void getAll() {
//        // given
//        var p1 = new Product("Coca-Cola", "Diet", BigDecimal.valueOf(5), 1);
//        var p2 = new Product("Pepsi", "", BigDecimal.valueOf(7), 1);
//        List<Product> actual = new ArrayList<>(Arrays.asList(p1, p2));
//        given(productRepository.findAll()).willReturn(actual);
//
//        // when
//        List<Product> expected = productService.getAll();
//
//        // then
//        assertEquals(expected.size(), actual.size());
//        assertEquals(expected, actual);
//    }

    @Test
    void getProductsByCategory_shouldReturnEmptyList() {
        // given
        var p1 = createTestProduct("Coca-Cola", "Diet", BigDecimal.valueOf(5));
        var p2 = createTestProduct("Pepsi", "", BigDecimal.valueOf(7));

        List<Product> list = new ArrayList<>(Arrays.asList(p1, p2));

        Pageable pageable = PageRequest.of(0,10);
        Page<Product> pages = new PageImpl<>(list, pageable, list.size());
        Page<Product> actual = new PageImpl<>(new ArrayList<>(), pageable, 0);

        given(productRepository.findProductsByCategoryId(1, pageable)).willReturn(pages);
        given(productRepository.findProductsByCategoryId(2, pageable)).willReturn(actual);

        // when
        Page<Product> expected = productService.getProductsByCategory(2, pageable);

        // then
        assertEquals(expected.getTotalElements(), actual.getTotalElements());
    }

    @Test
    void getProductsByCategory() {
        // given
        var p1 = createTestProduct("Coca-Cola", "Diet", BigDecimal.valueOf(5));
        var p2 = createTestProduct("Pepsi", "", BigDecimal.valueOf(7));

        List<Product> list = new ArrayList<>(Arrays.asList(p1, p2));

        Pageable pageable = PageRequest.of(0,10);
        Page<Product> actual = new PageImpl<>(list, pageable, list.size());

        given(productRepository.findProductsByCategoryId(1, pageable)).willReturn(actual);

        // when
        Page<Product> expected = productService.getProductsByCategory(1, pageable);

        // then
        assertEquals(expected.getTotalElements(), actual.getTotalElements());
    }

    @Test()
    void getProductById_shouldThrowEntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> productService.getProductById(2));

        assertTrue(exception.getMessage().contains("Product not found"));
    }

    @Test()
    void getProductById() {
        // given
        var actual = createTestProduct("Coca-Cola", "Diet", BigDecimal.valueOf(5));
        actual.setId(1);

        given(productRepository.findProductById(1)).willReturn(actual);

        // when
        Product expected = productService.getProductById(1);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void addProduct() {
        // given
        var unsaved = createTestProduct("Coca-Cola", "Diet", BigDecimal.valueOf(5));
        var saved = createTestProduct("Coca-Cola", "Diet", BigDecimal.valueOf(5));
        saved.setId(1);

        given(productRepository.save(unsaved)).willReturn(saved);

        // when
        Product expected = productService.addProduct(unsaved);

        // then
        assertEquals(expected, saved);
    }

    private Product createTestProduct(String name, String description, BigDecimal price) {
        var product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(createTestCategory());
        return product;
    }

    private Category createTestCategory() {
        var category = new Category();
        category.setId(1);
        category.setName("Test");
        return category;
    }
}