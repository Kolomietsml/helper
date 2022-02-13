package academy.productstore.api;

import academy.productstore.domain.Category;
import academy.productstore.domain.Product;
import academy.productstore.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ProductResourceUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService mockService;

    @Test
    void getProductsByCategory_shouldReturnsPageWithProducts() throws Exception {
        // given
        var product = createTestProduct();
        Pageable pageable = PageRequest.of(0,20);
        Page<Product> products = new PageImpl<>(List.of(product), pageable, 1);
        when(mockService.getProductsByCategory(1, pageable)).thenReturn(products);

        // when

        // then
        mockMvc.perform(get("/api/v1/categories/1/products").contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("_embedded.products", hasSize(1)))
                .andExpect(jsonPath("_embedded.products[0].name").value("Coca-Cola"))
                .andExpect(jsonPath("_embedded.products[0]._links.self.href", containsString("/products/2")))
                .andExpect(jsonPath("_embedded.products[0]._links.self.type").value("GET"))
                .andExpect(jsonPath("_embedded.products[0].category._links.self.href", containsString("categories/1/products")))
                .andExpect(jsonPath("_embedded.products[0].category._links.self.type").value("GET"))
                .andExpect(jsonPath("_links.self.href", containsString("/categories/1/products")))
                .andDo(print());

        verify(mockService, times(1)).getProductsByCategory(1, pageable);
    }

    @Test
    void search_shouldReturnsPageWithProducts() throws Exception {
        // given
        var product = createTestProduct();
        Pageable pageable = PageRequest.of(0,20);
        Page<Product> products = new PageImpl<>(List.of(product), pageable, 1);
        when(mockService.search("Co", pageable)).thenReturn(products);

        // when

        // then
        mockMvc.perform(get("/api/v1/products/search?keyword=Co").contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("_embedded.products", hasSize(1)))
                .andExpect(jsonPath("_embedded.products[0].name").value("Coca-Cola"))
                .andExpect(jsonPath("_embedded.products[0]._links.self.href", containsString("/products/2")))
                .andExpect(jsonPath("_embedded.products[0]._links.self.type").value("GET"))
                .andExpect(jsonPath("_embedded.products[0].category._links.self.href", containsString("categories/1/products")))
                .andExpect(jsonPath("_embedded.products[0].category._links.self.type").value("GET"))
                .andExpect(jsonPath("_links.self.href", containsString("/products/search?keyword=Co")))
                .andDo(print());

        verify(mockService, times(1)).search("Co", pageable);
    }

    @Test
    void getProductById_shouldThrowsEntityNotFoundException() throws Exception {
        // given
        when(mockService.getProductById(1)).thenThrow(new EntityNotFoundException("Product not found"));

        // when

        // then
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("Product not found", Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(content().string(equalTo("Product not found")))
                .andDo(print());

        verify(mockService, times(1)).getProductById(1);
    }

    @Test
    void getProductById_shouldReturnsProduct() throws Exception {
        // given
        var product = createTestProduct();
        when(mockService.getProductById(product.getId())).thenReturn(product);

        // when

        // then
        mockMvc.perform(get("/api/v1/products/{id}", product.getId()).contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("name").value("Coca-Cola"))
                .andExpect(jsonPath("_links.self.href", containsString("/products/2")))
                .andExpect(jsonPath("_links.self.type").value("GET"))
                .andExpect(jsonPath("category._links.self.href", containsString("categories/1/products")))
                .andExpect(jsonPath("category._links.self.type").value("GET"))
                .andExpect(jsonPath("_links.self.href", containsString("/products/2")))
                .andDo(print());

        verify(mockService, times(1)).getProductById(product.getId());
    }

    private Product createTestProduct() {
        var category = new Category();
        category.setId(1L);
        category.setName("Beverages");

        var product = new Product();
        product.setId(2L);
        product.setName("Coca-Cola");
        product.setDescription("");
        product.setPrice(BigDecimal.valueOf(0.5));
        product.setCategory(category);

        return product;
    }
}