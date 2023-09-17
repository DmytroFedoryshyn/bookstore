package bookstore.dto.cartItem;

import lombok.Data;

@Data
public class UpdateCartItemDto {
    private Long bookId;
    private int quantity;
}
