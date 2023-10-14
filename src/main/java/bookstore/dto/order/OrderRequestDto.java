package bookstore.dto.order;

import bookstore.dto.orderitem.OrderItemRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import lombok.Data;

@Data
public class OrderRequestDto {
    @NotBlank
    private String shippingAddress;
    @NotEmpty
    private Set<OrderItemRequestDto> orderItems;
}
