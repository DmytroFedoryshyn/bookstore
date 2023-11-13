package bookstore.controller.book;

import bookstore.dto.book.BookResponseDto;
import bookstore.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
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
class BookControllerTest {
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
    @DisplayName("Get all books successfully")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAll_Success() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn();

        List<BookResponseDto> actualList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(3, actualList.size());
    }

    @Test
    @DisplayName("Get book by id successfully")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getById_Success() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/books/1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn();

        BookResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), BookResponseDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.getId());
    }

    @Test
    @DisplayName("Create new book successfully")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Sql(scripts = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createNewBook_Success() throws Exception {
        CreateBookRequestDto dto = new CreateBookRequestDto();
        dto.setTitle("Book title");
        dto.setPrice(BigDecimal.TEN);
        dto.setAuthor("Author");
        dto.setDescription("desc");
        dto.setCoverImage("image");
        dto.setIsbn("978-3-16-148410-0");
        dto.setCategories(Set.of(1L));

        BookResponseDto expected = new BookResponseDto();
        expected.setTitle(dto.getTitle());
        expected.setPrice(dto.getPrice());
        expected.setAuthor(dto.getAuthor());
        expected.setDescription(dto.getDescription());
        expected.setCoverImage(dto.getCoverImage());
        expected.setIsbn(dto.getIsbn());
        expected.setCategories(dto.getCategories());


        MvcResult result = mockMvc.perform(
                post("/api/books").
                    content(objectMapper.writeValueAsString(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andReturn();

        BookResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), BookResponseDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Update book successfully")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Sql(scripts = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateBook_Success() throws Exception {
        CreateBookRequestDto dto = new CreateBookRequestDto();
        dto.setTitle("Book title");
        dto.setPrice(BigDecimal.TEN);
        dto.setAuthor("Updated Author");
        dto.setDescription("desc");
        dto.setCoverImage("image");
        dto.setIsbn("978-3-16-148410-0");
        dto.setCategories(Set.of(1L));


        MvcResult result = mockMvc.perform(
                put("/api/books/1").
                    content(objectMapper.writeValueAsString(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn();

        BookResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), BookResponseDto.class);


        Assertions.assertNotNull(result);
        EqualsBuilder.reflectionEquals(dto, actual);
    }

    @Test
    @DisplayName("Update non existent book returns 404")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Sql(scripts = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateNonExistentBook_Returns_404() throws Exception {
        CreateBookRequestDto dto = new CreateBookRequestDto();
        dto.setTitle("Book title");
        dto.setPrice(BigDecimal.TEN);
        dto.setAuthor("Updated Author");
        dto.setDescription("desc");
        dto.setCoverImage("image");
        dto.setIsbn("978-3-16-148410-0");
        dto.setCategories(Set.of(1L));


        mockMvc.perform(
                put("/api/books/45").
                    content(objectMapper.writeValueAsString(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound())
            .andReturn();
    }
}