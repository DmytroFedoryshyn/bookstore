package bookstore.repository.orderItem;

import bookstore.model.OrderItem;
import bookstore.repository.orderitem.OrderItemRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderItemRepositoryTest {
    @Autowired
    private OrderItemRepository repository;

    @Test
    @DisplayName("Find all order items by order")
    @Sql(value = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertUser.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertCartAndItem.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:addOrderAndOrderItem.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

    @Sql(value = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteCartAndItem.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteOrderAndOrderItem.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getOrderItemsByOrder_Success() {
        List<OrderItem> list = repository.getAllByOrderId(1L);
        Assertions.assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Get order items by non-existent order ID (expect failure)")
    public void getOrderItemsByNonExistentOrder_Failure() {
        List<OrderItem> list = repository.getAllByOrderId(999L);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("Find order item by id and order id")
    @Sql(value = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertUser.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertCartAndItem.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:addOrderAndOrderItem.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

    @Sql(value = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteCartAndItem.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteOrderAndOrderItem.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByOrderIdAndOrderItemId_Success() {
        OrderItem orderItem = repository.findByOrderIdAndOrderItemId(1L, 1L);
        Assertions.assertEquals(1, orderItem.getId());
        Assertions.assertEquals(1, orderItem.getOrder().getId());
    }

    @Test
    @DisplayName("Find order item by non-existent order ID and order item ID (expect failure)")
    public void findByNonExistentOrderIdAndNonExistentOrderItemId_Failure() {
        Assertions.assertNull(repository.findByOrderIdAndOrderItemId(999L, 999L));
    }
}
