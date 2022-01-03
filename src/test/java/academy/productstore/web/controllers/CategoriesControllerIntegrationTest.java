package academy.productstore.web.controllers;

import academy.productstore.persistence.entity.Category;
import academy.productstore.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
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
    void getCategories() throws Exception {

        // given
        createTestCategory("Beverages");
        createTestCategory("Fats and oils");

        String url = "/categories";

        // when

        // then
        mockMvc.perform(get(url).contentType(MediaTypes.HAL_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("_embedded.categories", hasSize(2)))

                .andExpect(jsonPath("_embedded.categories[0].name").value("Beverages"))
                .andExpect(jsonPath("_embedded.categories[0]._links.self.href", containsString("/categories/1/products")))
                .andExpect(jsonPath("_embedded.categories[0]._links.self.type").value("GET"))

                .andExpect(jsonPath("_embedded.categories[1].name").value("Fats and oils"))
                .andExpect(jsonPath("_embedded.categories[1]._links.self.href", containsString("/categories/2/products")))
                .andExpect(jsonPath("_embedded.categories[1]._links.self.type").value("GET"))

                .andExpect(jsonPath("_links.self.href", containsString(url)))
                .andExpect(jsonPath("_links.self.type").value("GET"))
                .andDo(print());
    }

    private void createTestCategory(String name) {
        var category = new Category();
        category.setName(name);
        categoryService.addCategory(category);
    }
}