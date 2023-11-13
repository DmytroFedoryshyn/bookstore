package bookstore.controller;

import bookstore.dto.category.CategoryResponseDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    protected static MockMvc mockMvc;
    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders.
            webAppContextSetup(applicationContext)
            .apply(springSecurity())
            .build();
    }

    @Test
    @DisplayName("Create new category successfully")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Sql(scripts = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createNewCategory_Success() throws Exception {
        CreateCategoryRequestDto dto = new CreateCategoryRequestDto();
        dto.setName("Category");
        dto.setDescription("Description");

        CategoryResponseDto expected = new CategoryResponseDto();
        expected.setName("Category");
        expected.setDescription("Description");


        MvcResult result = mockMvc.perform(
                post("/api/categories").
                    content(objectMapper.writeValueAsString(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryResponseDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Get all categories successfully")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAll_Success() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn();

        List<CategoryResponseDto> actualList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(2, actualList.size());
    }

    @Test
    @DisplayName("Get category by id successfully")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getById_Success() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/categories/1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryResponseDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.getId());
    }






    @Test
    @DisplayName("Update category successfully")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Sql(scripts = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateBook_Success() throws Exception {
        CreateCategoryRequestDto dto = new CreateCategoryRequestDto();
        dto.setName("New name");
        dto.setDescription("New description");


        MvcResult result = mockMvc.perform(
                put("/api/categories/1").
                    content(objectMapper.writeValueAsString(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryResponseDto.class);


        Assertions.assertNotNull(result);
        EqualsBuilder.reflectionEquals(dto, actual);
    }

    @Test
    @DisplayName("Update non existent category returns 404")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Sql(scripts = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateNonExistentBook_Returns_404() throws Exception {
        CreateCategoryRequestDto dto = new CreateCategoryRequestDto();
        dto.setName("New name");
        dto.setDescription("New description");


        mockMvc.perform(
                put("/api/categories/45").
                    content(objectMapper.writeValueAsString(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound())
            .andReturn();
    }
}
