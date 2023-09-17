package bookstore.dto.cart;

import bookstore.dto.cartItem.CartItemResponseDto;
import java.util.Set;
import lombok.Data;

@Data
public class CartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItemResponseDto> items;
}
