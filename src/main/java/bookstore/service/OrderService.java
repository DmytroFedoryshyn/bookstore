package bookstore.service;

import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.OrderResponseDto;
import bookstore.dto.order.OrderUpdateDto;
import bookstore.model.User;
import java.util.List;

public interface OrderService {
    OrderResponseDto save(OrderRequestDto dto, User user);

    List<OrderResponseDto> getByUser(User user, int page, int size, String sort);

    OrderResponseDto update(OrderUpdateDto dto, Long id);
}
