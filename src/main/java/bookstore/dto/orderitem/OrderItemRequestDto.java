package bookstore.dto.orderitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequestDto {
    @NotNull
    private Long bookId;
    @Min(1)
    private int quantity;
}
