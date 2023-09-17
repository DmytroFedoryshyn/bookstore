package bookstore.repository.shoppingcart;

import bookstore.model.ShoppingCart;
import bookstore.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = {"items", "items.book"})
    ShoppingCart getByUser(@Param("user") User user);
}
