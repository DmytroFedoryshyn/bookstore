package bookstore.service;

import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.OrderResponseDto;
import bookstore.dto.order.OrderUpdateDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto save(OrderRequestDto dto, String username);

    List<OrderResponseDto> getByUser(String username, int page, int size, String sort);

    OrderResponseDto update(OrderUpdateDto dto, Long id);
}
