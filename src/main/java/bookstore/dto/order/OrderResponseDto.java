package bookstore.dto.order;

import bookstore.dto.orderitem.OrderItemResponseDto;
import bookstore.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Order.Status status;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private Set<OrderItemResponseDto> orderItems = new HashSet<>();
}
