package bookstore.repository.orderitem;

import bookstore.model.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi "
            + "JOIN FETCH oi.order o "
            + "JOIN FETCH oi.book b "
            + "WHERE o.id = :orderId")
    List<OrderItem> getAllByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT oi FROM OrderItem oi "
            + "JOIN FETCH oi.order o "
            + "WHERE o.id = :orderId "
            + "AND oi.id = :orderItemId")
    OrderItem findByOrderIdAndOrderItemId(
            @Param("orderId") Long orderId,
            @Param("orderItemId") Long orderItemId
    );
}
