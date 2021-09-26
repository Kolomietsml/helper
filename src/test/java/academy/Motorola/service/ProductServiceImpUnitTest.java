package academy.Motorola.service;

import academy.Motorola.entity.Product;
import academy.Motorola.repository.ProductRepository;
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

class ProductServiceImpUTest {

    private ProductRepository productRepository = mock(ProductRepository.class);

    private ProductService productService = new ProductServiceImpl(productRepository);

    @Test
    void getAll_shouldReturnEmptyList() {
        // given
        List<Product> products;

        //when
        products = productService.getAll();

        // then
        assertThat(products).isEmpty();
    }

    @Test
    void getAll() {
        // given
        var p1 = new Product("Coca-Cola", "Diet", new BigDecimal(5), 1);
        var p2 = new Product("Pepsi", "", new BigDecimal(7), 1);
        List<Product> result = new ArrayList<>(Arrays.asList(p1, p2));

        // when
        given(productRepository.findAll()).willReturn(result);

        // then
        assertEquals(productService.getAll().size(), 2);
    }

    @Test
    void getProductsByCategory_shouldReturnEmptyList() {
        // given
        var p1 = new Product("Coca-Cola", "Diet", new BigDecimal(5), 1);
        var p2 = new Product("Pepsi", "", new BigDecimal(7), 1);
        List<Product> result = new ArrayList<>(Arrays.asList(p1, p2));

        // when
        given(productRepository.findProductsByCategoryId(1)).willReturn(result);

        // then
        assertThat(productService.getProductsByCategory(2)).isEmpty();
    }

    @Test
    void getProductsByCategory() {
        // given
        var p1 = new Product("Coca-Cola", "Diet", new BigDecimal(5), 1);
        var p2 = new Product("Pepsi", "", new BigDecimal(7), 1);
        List<Product> result = new ArrayList<>(Arrays.asList(p1, p2));

        // when
        given(productRepository.findProductsByCategoryId(1)).willReturn(result);

        // then
        assertEquals(productService.getProductsByCategory(1).size(), 2);
    }

    @Test()
    void getProductById_shouldThrowEntityNotFoundException() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> productService.getProductById(2));

        assertTrue(exception.getMessage().contains("Product not found"));
    }

    @Test()
    void getProductById() {
        // given
        var expected = new Product("Coca-Cola", "Diet", new BigDecimal(5), 1);
        expected.setId(1);
        var actual = new Product("Coca-Cola", "Diet", new BigDecimal(5), 1);
        actual.setId(1);

        // when
        given(productRepository.findProductById(1)).willReturn(expected);

        // then
        assertEquals(productService.getProductById(1),  actual);
    }

    @Test
    void addProduct() {
        var unsaved = new Product("Coca-Cola", "Diet", new BigDecimal(5), 1);
        var saved = new Product("Coca-Cola", "Diet", new BigDecimal(5), 1);
        saved.setId(1);

        given(productRepository.save(unsaved)).willReturn(saved);

        assertEquals(productService.addProduct(unsaved), saved);
    }
}