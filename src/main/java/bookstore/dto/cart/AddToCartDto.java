package bookstore.dto.cart;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AddToCartDto {
    private Long bookId;
    @Min(1)
    private int quantity;
}
