package academy.productstore.controllers.admin;

import academy.productstore.entity.Category;
import academy.productstore.entity.Product;
import academy.productstore.service.ProductService;
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
import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminProductsControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void addProduct() throws Exception {
        // given
        var product = createTestProduct();
        when(productService.addProduct(any())).thenReturn(product);

        // when then
        mockMvc.perform(
                post("/api/admin/products")
                        .content(objectMapper.writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }

    @Test
    void getProduct() throws Exception {
        // given
        var product = createTestProduct();
        when(productService.getProductById(product.getId())).thenReturn(product);

        // when then
        mockMvc.perform(
                get("/api/admin/products/{id}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("Coca-Cola"))
                .andExpect(jsonPath("description").value(""))
                .andExpect(jsonPath("price").value(5.0));
                //.andExpect(jsonPath("categoryId").value(1));
    }

    @Test
    void getProduct_shouldThrowEntityNotFoundException() throws Exception {
        // given
        when(productService.getProductById(anyLong())).thenThrow(new EntityNotFoundException("Product not found"));

        // when then
        mockMvc.perform(
                get("/api/admin/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("Product not found", Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(content().string(equalTo("Product not found")))
                .andDo(print());
    }

//    @Test
//    void updateProduct() throws Exception {
//        // given
//        var product = createTestProduct();
//        var p = new Product("Coca-Cola", "Diet", BigDecimal.valueOf(7), 1);
//        //var p = new Product("Coca-Cola", "Diet", BigDecimal.valueOf(7), createTestCategory());
//
//        when(productService.addProduct(product)).thenReturn(product);
//        when(productService.updateProduct(p, product.getId())).thenReturn(p);
//
//        // when then
//        mockMvc.perform(
//                put("/api/admin/products/{id}", product.getId())
//                        .content(objectMapper.writeValueAsString(p))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").isNumber())
//                .andExpect(jsonPath("name").value("Coca-Cola"))
//                .andExpect(jsonPath("description").value("Diet"))
//                .andExpect(jsonPath("price").value(7.0))
//                .andExpect(jsonPath("categoryId").value(1));
//    }

    @Test
    void deleteProduct() throws Exception {
        //given

        //when then
        mockMvc.perform(
                delete("/api/admin/products/1"))
                .andExpect(status().isNoContent());
    }

    private Product createTestProduct(){
        var product = new Product();
        product.setId(1);
        product.setName("Coca-Cola");
        product.setDescription("");
        product.setPrice(BigDecimal.valueOf(5));

        product.setCategory(createTestCategory());

        return product;
    }

    private Category createTestCategory() {
        var c = new Category();
        c.setId(2);
        c.setName("Beverages");
        return c;
    }
}