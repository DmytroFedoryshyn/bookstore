package bookstore.repository.order;

import bookstore.model.Order;
import bookstore.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "user"})
    List<Order> getAllByUser(User user, Pageable pageable);
}
