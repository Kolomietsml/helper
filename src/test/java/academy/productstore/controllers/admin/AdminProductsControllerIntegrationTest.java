package academy.productstore.controllers.admin;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class AdminProductsControllerIntegrationTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @AfterEach
//    void resetDb() {
//        productService.deleteAll();
//    }
//
//    @Test
//    void addProduct() throws Exception {
//        // given
//        var product = new Product("Coca-Cola", "", BigDecimal.valueOf(5), 1);
//
//        // when then
//        mockMvc.perform(
//                post("/api/admin/products")
//                        .content(objectMapper.writeValueAsString(product))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").isNumber())
//                .andExpect(jsonPath("name").value("Coca-Cola"))
//                .andExpect(jsonPath("description").value(""))
//                .andExpect(jsonPath("price").value(5.0))
//                .andExpect(jsonPath("categoryId").value(1));
//    }
//
//    @Test
//    void getProduct() throws Exception {
//        // given
//        long id = createTestProduct().getId();
//
//        // when then
//        mockMvc.perform(
//                get("/api/admin/products/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").isNumber())
//                .andExpect(jsonPath("name").value("Coca-Cola"))
//                .andExpect(jsonPath("description").value(""))
//                .andExpect(jsonPath("price").value(5.0))
//                .andExpect(jsonPath("categoryId").value(1));
//    }
//
//    @Test
//    void getProduct_shouldThrowEntityNotFoundException() throws Exception {
//        //given
//
//        //when then
//        mockMvc.perform(
//                get("/api/admin/products/2"))
//                .andExpect(status().isNotFound())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
//                .andExpect(result -> assertEquals("Product not found", Objects.requireNonNull(result.getResolvedException()).getMessage()))
//                .andDo(print());
//
//    }
//
//    @Test
//    void updateProduct() throws Exception {
//        // given
//        long id = createTestProduct().getId();
//
//        //when then
//        mockMvc.perform(
//                put("/api/admin/products/{id}", id)
//                        .content(objectMapper.writeValueAsString(new Product("Coca-Cola", "Diet", new BigDecimal(7), 1)))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").isNumber())
//                .andExpect(jsonPath("name").value("Coca-Cola"))
//                .andExpect(jsonPath("description").value("Diet"))
//                .andExpect(jsonPath("price").value(7.0))
//                .andExpect(jsonPath("categoryId").value(1));
//    }
//
//    @Test
//    void deleteProduct() throws Exception {
//        // given
//        var product = createTestProduct();
//
//        // when then
//        mockMvc.perform(
//                delete("/api/admin/products/{id}", product.getId()))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void getProducts() throws Exception {
//        // given
//        var p1 = createTestProduct();
//        var p2 = createTestProduct();
//
//        // when then
//        mockMvc.perform(
//                get("/api/admin/products"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(p1, p2))));
//
//    }
//
//    private Product createTestProduct() {
//        var product = new Product("Coca-Cola", "", BigDecimal.valueOf(5), 1);
//        return productService.addProduct(product);
//    }
}