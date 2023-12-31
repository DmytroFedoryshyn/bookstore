package bookstore.repository.cartitem;

import bookstore.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @EntityGraph(attributePaths = {"book"})
    Optional<CartItem> getCartItemById(@Param("cartItemId") Long cartItemId);
}
