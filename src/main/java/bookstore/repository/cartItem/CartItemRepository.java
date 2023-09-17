package bookstore.repository.cartItem;

import bookstore.model.CartItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @EntityGraph(attributePaths = {"book"})
    CartItem getReferenceById(@Param("cartItemId") Long cartItemId);

}
