package academy.productstore.web.controllers;

import academy.productstore.persistence.entity.Category;
import academy.productstore.persistence.entity.Product;
import academy.productstore.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService service;

    @AfterEach
    void resetDb() {
        service.deleteAll();
    }

    @Test
    void getProductsByCategory_shouldReturnsPageWithProducts() throws Exception {
        // given
        var product = createTestProduct("Coca-Cola", "Diet", BigDecimal.valueOf(0.5));

        String categoriesUrl = String.format("/categories/%d/products", product.getCategory().getId());
        String productsUrl = String.format("/products/%d", product.getId());

        // when

        // then
        mockMvc.perform(get(categoriesUrl).contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("_embedded.products", hasSize(1)))
                .andExpect(jsonPath("_embedded.products[0].name").value("Coca-Cola"))
                .andExpect(jsonPath("_embedded.products[0]._links.self.href", containsString(productsUrl)))
                .andExpect(jsonPath("_embedded.products[0]._links.self.type").value("GET"))
                .andExpect(jsonPath("_embedded.products[0].category._links.self.href", containsString(categoriesUrl)))
                .andExpect(jsonPath("_embedded.products[0].category._links.self.type").value("GET"))
                .andExpect(jsonPath("_embedded.products[0]._links.add_to_cart.href", containsString("/cart")))
                .andExpect(jsonPath("_embedded.products[0]._links.add_to_cart.type").value("POST"))
                .andExpect(jsonPath("_links.self.href", containsString(categoriesUrl)))
                .andDo(print());
    }

    @Test
    void search_shouldReturnsPageWithProducts() throws Exception {
        // given
        var product = createTestProduct("Coca-Cola", "Diet", BigDecimal.valueOf(0.5));

        String searchUrl = "/products/search?keyword=Co";
        String categoriesUrl = String.format("/categories/%d/products", product.getCategory().getId());
        String productsUrl = String.format("/products/%d", product.getId());

        // when

        // then
        mockMvc.perform(get(searchUrl).contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("_embedded.products", hasSize(1)))
                .andExpect(jsonPath("_embedded.products[0].name").value("Coca-Cola"))
                .andExpect(jsonPath("_embedded.products[0]._links.self.href", containsString(productsUrl)))
                .andExpect(jsonPath("_embedded.products[0]._links.self.type").value("GET"))
                .andExpect(jsonPath("_embedded.products[0].category._links.self.href", containsString(categoriesUrl)))
                .andExpect(jsonPath("_embedded.products[0].category._links.self.type").value("GET"))
                .andExpect(jsonPath("_embedded.products[0]._links.add_to_cart.href", containsString("/cart")))
                .andExpect(jsonPath("_embedded.products[0]._links.add_to_cart.type").value("POST"))
                .andExpect(jsonPath("_links.self.href", containsString(searchUrl)))
                .andDo(print());
    }

    @Test
    void getProductById_shouldThrowsEntityNotFoundException() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("Product not found", Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(content().string(equalTo("Product not found")))
                .andDo(print());
    }

    @Test
    void getProductById_shouldReturnsProduct() throws Exception {
        // given
        var product = createTestProduct("Pepsi", "", BigDecimal.valueOf(0.45));

        String categoriesUrl = String.format("/categories/%d/products", product.getCategory().getId());
        String productsUrl = String.format("/products/%d", product.getId());


        // when

        // then
        mockMvc.perform(get(productsUrl).contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("name").value("Pepsi"))
                .andExpect(jsonPath("_links.self.href", containsString(productsUrl)))
                .andExpect(jsonPath("_links.self.type").value("GET"))
                .andExpect(jsonPath("category._links.self.href", containsString(categoriesUrl)))
                .andExpect(jsonPath("category._links.self.type").value("GET"))
                .andExpect(jsonPath("_links.add_to_cart.href", containsString("/cart")))
                .andExpect(jsonPath("_links.add_to_cart.type").value("POST"))
                .andExpect(jsonPath("_links.self.href", containsString(productsUrl)))
                .andDo(print());
    }

    private Product createTestProduct(String name, String desc, BigDecimal price) {
        var category = new Category();
        category.setName("Beverages");

        var product = new Product();
        product.setName(name);
        product.setDescription(desc);
        product.setPrice(price);
        product.setCategory(category);

        return service.addProduct(product);
    }
}