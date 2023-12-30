package bookstore.repository.cartItem;

import bookstore.model.CartItem;
import bookstore.repository.cartitem.CartItemRepository;
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
public class CartItemRepositoryTest {
    @Autowired
    private CartItemRepository repository;

    @Test
    @DisplayName("Find cart item by id")
    @Sql(value = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertUser.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertCartAndItem.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

    @Sql(value = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteCartAndItem.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findCartItemById_Ok() {
        Optional<CartItem> cartItemOptional = repository.getCartItemById(1L);
        Assertions.assertTrue(cartItemOptional.isPresent());
    }

    @Test
    @DisplayName("Find cart item by non-existent id returns empty optional")
    void findCartItemById_ReturnsEmptyOptional() {
        Optional<CartItem> cartItemOptional = repository.getCartItemById(1L);
        Assertions.assertTrue(cartItemOptional.isEmpty());
    }
}
