package academy.productstore.controllers;

import academy.productstore.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
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
    @Sql({"/import_categories.sql", "/import_products.sql"})
    void getProductsByCategory_shouldReturnsPageWithProducts() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get("/categories/1/products").contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("_embedded.products", hasSize(1)))
                .andExpect(jsonPath("_embedded.products[0].name").value("Coca-Cola"))
                .andExpect(jsonPath("_embedded.products[0]._links.self.href", containsString("/products/2")))
                .andExpect(jsonPath("_embedded.products[0]._links.self.type").value("GET"))
                .andExpect(jsonPath("_embedded.products[0].category._links.self.href", containsString("/categories/1/products")))
                .andExpect(jsonPath("_embedded.products[0].category._links.self.type").value("GET"))
                .andExpect(jsonPath("_embedded.products[0]._links.add_to_cart.href", containsString("/cart")))
                .andExpect(jsonPath("_embedded.products[0]._links.add_to_cart.type").value("POST"))
                .andExpect(jsonPath("_links.self.href", containsString("/categories/1/products")))
                .andDo(print());
    }

    @Test
    @Sql({"/import_categories.sql", "/import_products.sql"})
    void search_shouldReturnsPageWithProducts() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get("/products/search?keyword=Co").contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("_embedded.products", hasSize(1)))
                .andExpect(jsonPath("_embedded.products[0].name").value("Coca-Cola"))
                .andExpect(jsonPath("_embedded.products[0]._links.self.href", containsString("/products/2")))
                .andExpect(jsonPath("_embedded.products[0]._links.self.type").value("GET"))
                .andExpect(jsonPath("_embedded.products[0].category._links.self.href", containsString("/categories/1/products")))
                .andExpect(jsonPath("_embedded.products[0].category._links.self.type").value("GET"))
                .andExpect(jsonPath("_embedded.products[0]._links.add_to_cart.href", containsString("/cart")))
                .andExpect(jsonPath("_embedded.products[0]._links.add_to_cart.type").value("POST"))
                .andExpect(jsonPath("_links.self.href", containsString("/products/search?keyword=Co")))
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
    @Sql({"/import_categories.sql", "/import_products.sql"})
    void getProductById_shouldReturnsProduct() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get("/products/2").contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("name").value("Coca-Cola"))
                .andExpect(jsonPath("_links.self.href", containsString("/products/2")))
                .andExpect(jsonPath("_links.self.type").value("GET"))
                .andExpect(jsonPath("category._links.self.href", containsString("/categories/1/products")))
                .andExpect(jsonPath("category._links.self.type").value("GET"))
                .andExpect(jsonPath("_links.add_to_cart.href", containsString("/cart")))
                .andExpect(jsonPath("_links.add_to_cart.type").value("POST"))
                .andExpect(jsonPath("_links.self.href", containsString("/products/2")))
                .andDo(print());
    }
}