package bookstore.service;

import bookstore.dto.orderitem.OrderItemResponseDto;
import bookstore.mapper.OrderItemMapper;
import bookstore.repository.orderitem.OrderItemRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemResponseDto> getAllByOrderId(Long id) {
        return orderItemRepository.getAllByOrderId(id)
            .stream()
            .map(orderItemMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public OrderItemResponseDto findByOrderIdAndOrderItemId(Long orderId, Long orderItemId) {
        return orderItemMapper.toDto(orderItemRepository
            .findByOrderIdAndOrderItemId(orderId, orderItemId));
    }
}
