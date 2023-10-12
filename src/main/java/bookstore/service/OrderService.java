package bookstore.service;

import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.OrderResponseDto;
import bookstore.dto.order.OrderUpdateDto;
import bookstore.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto save(OrderRequestDto dto, User user);

    List<OrderResponseDto> getByUser(User user, Pageable pageable);

    OrderResponseDto update(OrderUpdateDto dto, Long id);
}
