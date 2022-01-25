package academy.productstore.controllers;

import academy.productstore.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriesControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

    @AfterEach
    void resetDb() {
        categoryService.deleteAll();
    }

    @Test
    @Sql({"/import_categories.sql"})
    void getCategories() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get("/categories").contentType(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("_embedded.categories", hasSize(1)))
                .andExpect(jsonPath("_embedded.categories[0].name").value("Beverages"))
                .andExpect(jsonPath("_embedded.categories[0]._links.self.href", containsString("/categories/1/products")))
                .andExpect(jsonPath("_embedded.categories[0]._links.self.type").value("GET"))
                .andExpect(jsonPath("_links.self.href", containsString("/categories")))
                .andExpect(jsonPath("_links.self.type").value("GET"))
                .andDo(print());
    }
}