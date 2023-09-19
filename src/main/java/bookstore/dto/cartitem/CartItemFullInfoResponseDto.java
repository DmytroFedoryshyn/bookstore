package bookstore.dto.cartitem;

import lombok.Data;

@Data
public class CartItemFullInfoResponseDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private int quantity;
    private Long shoppingCartId;
}
