package bookstore.service;

import bookstore.dto.orderitem.OrderItemResponseDto;
import java.util.List;

public interface OrderItemService {
    List<OrderItemResponseDto> getAllByOrderId(Long orderId);

    OrderItemResponseDto findByOrderIdAndOrderItemId(Long orderId, Long orderItemId);
}
