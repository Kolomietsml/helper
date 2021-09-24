package academy.Motorola.controllers.admin;

import academy.Motorola.entity.Product;
import academy.Motorola.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminProductsControllerUTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void addProduct() throws Exception {
        var product = createTestProduct();

        when(productService.addProduct(any())).thenReturn(product);

        mockMvc.perform(
                post("/api/admin/products")
                        .content(objectMapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }

    @Test
    void getProduct() throws Exception {
        var product = createTestProduct();

        when(productService.getProductById(product.getId())).thenReturn(product);

        mockMvc.perform(
                get("/api/admin/products/{id}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("Coca-Cola"))
                .andExpect(jsonPath("description").value(""))
                .andExpect(jsonPath("price").value(5.0))
                .andExpect(jsonPath("categoryId").value(1));
    }

    @Test
    void getProduct_EntityNotFoundException() throws Exception {

        when(productService.getProductById(anyLong())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                get("/api/admin/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));
    }

    @Test
    void updateProduct() throws Exception {
        var product = createTestProduct();

        var p = new Product("Coca-Cola", "Diet", new BigDecimal(7), 1);

        when(productService.addProduct(product)).thenReturn(product);
        when(productService.updateProduct(p, product.getId())).thenReturn(p);

        mockMvc.perform(
                put("/api/admin/products/{id}", product.getId())
                        .content(objectMapper.writeValueAsString(p))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("name").value("Coca-Cola"))
                .andExpect(jsonPath("description").value("Diet"))
                .andExpect(jsonPath("price").value(7.0))
                .andExpect(jsonPath("categoryId").value(1));
    }

    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(
                delete("/api/admin/products/1"))
                .andExpect(status().isNoContent());
    }

    private Product createTestProduct(){
        var product = new Product();
        product.setId(1);
        product.setName("Coca-Cola");
        product.setDescription("");
        product.setPrice(new BigDecimal(5));
        product.setCategoryId(1);
        return product;
    }
}