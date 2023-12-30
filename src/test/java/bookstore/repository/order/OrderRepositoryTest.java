package bookstore.repository.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository repository;

    @Test
    @DisplayName("Find all orders by user")
    @Sql(value = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertUser.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertCartAndItem.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:addOrderAndOrderItem.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

    @Sql(value = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteCartAndItem.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteOrderAndOrderItem.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllOrdersByUser_Success() {
        Assertions.assertEquals(1, repository.getAllByUserEmail("user@example.com", null).size());
    }

    @Test
    @DisplayName("Find all orders by non-existent user failure")
    public void getAllOrdersByUser_Failure() {
        Assertions.assertTrue(repository.getAllByUserEmail("non-existent@example.com", null).isEmpty());
    }
}
