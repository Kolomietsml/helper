package academy.productstore.service;

import academy.productstore.entity.Product;
import academy.productstore.repository.ProductRepository;
import org.junit.jupiter.api.Test;

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

    private ProductRepository productRepository = mock(ProductRepository.class);

    private ProductService productService = new ProductServiceImpl(productRepository);

    @Test
    void getAll_shouldReturnEmptyList() {
        // given

        // when
        List<Product> expected = productService.getAll();

        // then
        assertThat(expected).isEmpty();
    }

    @Test
    void getAll() {
        // given
        var p1 = new Product("Coca-Cola", "Diet", BigDecimal.valueOf(5), 1);
        var p2 = new Product("Pepsi", "", BigDecimal.valueOf(7), 1);
        List<Product> actual = new ArrayList<>(Arrays.asList(p1, p2));
        given(productRepository.findAll()).willReturn(actual);

        // when
        List<Product> expected = productService.getAll();

        // then
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void getProductsByCategory_shouldReturnEmptyList() {
        // given
        var p1 = new Product("Coca-Cola", "Diet", BigDecimal.valueOf(5), 1);
        var p2 = new Product("Pepsi", "", BigDecimal.valueOf(7), 1);
        List<Product> actual = new ArrayList<>(Arrays.asList(p1, p2));
        given(productRepository.findProductsByCategoryId(1)).willReturn(actual);

        // when
        List<Product> expected = productService.getProductsByCategory(2);

        // then
        assertThat(expected).isEmpty();
    }

    @Test
    void getProductsByCategory() {
        // given
        var p1 = new Product("Coca-Cola", "Diet", BigDecimal.valueOf(5), 1);
        var p2 = new Product("Pepsi", "", BigDecimal.valueOf(7), 1);
        List<Product> actual = new ArrayList<>(Arrays.asList(p1, p2));
        given(productRepository.findProductsByCategoryId(1)).willReturn(actual);

        // when
        List<Product> expected = productService.getProductsByCategory(1);

        // then
        assertEquals(expected.size(), actual.size());
    }

    @Test()
    void getProductById_shouldThrowEntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> productService.getProductById(2));

        assertTrue(exception.getMessage().contains("Product not found"));
    }

    @Test()
    void getProductById() {
        // given
        var actual = new Product("Coca-Cola", "Diet", BigDecimal.valueOf(5), 1);
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
        var unsaved = new Product("Coca-Cola", "Diet", BigDecimal.valueOf(5), 1);
        var saved = new Product("Coca-Cola", "Diet", BigDecimal.valueOf(5), 1);
        saved.setId(1);
        given(productRepository.save(unsaved)).willReturn(saved);

        // when
        Product expected = productService.addProduct(unsaved);

        // then
        assertEquals(expected, saved);
    }
}