package bookstore.repository.book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository repository;

    @Test
    @DisplayName("Find all books by category ID returns two books")
    @Sql(value = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoryId_Returns_2() {
        Assertions.assertEquals(2, repository.findAllByCategoryId(1L, PageRequest.of(0, 10)).size());
    }

    @Test
    @DisplayName("Find all books by non-existent category ID returns empty list")
    @Sql(value = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByNonExistentCategoryId_ReturnsEmptyList() {
        Assertions.assertEquals(0, repository.findAllByCategoryId(3L, PageRequest.of(0, 10)).size());
    }
}