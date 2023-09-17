package bookstore.dto.cart;

import lombok.Data;

@Data
public class AddToCartDto {
    private Long bookId;
    private int quantity;
}
