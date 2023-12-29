package bookstore.repository.user;

import bookstore.model.User;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("Find user by email")
    @Sql(value = "classpath:insertUser.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUserByEmail_Success() {
        Optional<User> result = repository.findByEmail("user@example.com");
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Find user by email")
    public void findUserByEmail_Failure() {
        Optional<User> result = repository.findByEmail("non-existent@example.com");
        Assertions.assertTrue(result.isEmpty());
    }
}
