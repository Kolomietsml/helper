package academy.productstore.web.controllers;

import academy.productstore.persistence.entity.Category;
import academy.productstore.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriesControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService mockService;

    @Test
    void getCategories() throws Exception {
        // given
        List<Category> categories = List.of(
                createTestCategory(1, "Beverages"),
                createTestCategory(2, "Fats and oils"));
        when(mockService.getAll()).thenReturn(categories);

        // when

        // then
        mockMvc.perform(get("/categories").contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("_embedded.categories", hasSize(2)))
                .andExpect(jsonPath("_embedded.categories[0].name").value("Beverages"))
                .andExpect(jsonPath("_embedded.categories[0]._links.self.href", containsString("/categories/1/products")))
                .andExpect(jsonPath("_embedded.categories[0]._links.self.type").value("GET"))
                .andExpect(jsonPath("_embedded.categories[1].name").value("Fats and oils"))
                .andExpect(jsonPath("_embedded.categories[1]._links.self.href", containsString("/categories/2/products")))
                .andExpect(jsonPath("_embedded.categories[1]._links.self.type").value("GET"))
                .andExpect(jsonPath("_links.self.href", containsString("/categories")))
                .andExpect(jsonPath("_links.self.type").value("GET"))
                .andDo(print());
    }

    private Category createTestCategory(long id, String name) {
        var category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
}