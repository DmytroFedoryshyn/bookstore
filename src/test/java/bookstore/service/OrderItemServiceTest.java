package bookstore.service;

import bookstore.dto.orderitem.OrderItemResponseDto;
import bookstore.mapper.OrderItemMapper;
import bookstore.model.OrderItem;
import bookstore.repository.orderitem.OrderItemRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {
    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private OrderItemMapper orderItemMapper;

    @Test
    @DisplayName("Get the user's order items by order id")
    public void getAllOrderItemsByOrder_Success() {
        Long orderId = 1L;
        List<OrderItem> orderItems = List.of(new OrderItem(), new OrderItem());

        when(orderItemRepository.getAllByOrderId(orderId)).thenReturn(orderItems);
        when(orderItemMapper.toDto(any(OrderItem.class))).thenReturn(new OrderItemResponseDto());

        List<OrderItemResponseDto> result = orderItemService.getAllByOrderId(orderId);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(orderItemRepository, times(1)).getAllByOrderId(eq(orderId));
        verify(orderItemMapper, times(2)).toDto(any(OrderItem.class));
        verifyNoMoreInteractions(orderItemRepository);
        verifyNoMoreInteractions(orderItemMapper);
    }

    @Test
    @DisplayName("Get the user's order item by order and order item id")
    public void findOrderItemByOrderAndItemId_Success() {
        Long orderId = 1L;
        Long orderItemId = 2L;
        OrderItem orderItem = new OrderItem();

        when(orderItemRepository.findByOrderIdAndOrderItemId(orderId, orderItemId)).thenReturn(orderItem);
        when(orderItemMapper.toDto(any(OrderItem.class))).thenReturn(new OrderItemResponseDto());

        OrderItemResponseDto result = orderItemService.findByOrderIdAndOrderItemId(orderId, orderItemId);

        assertNotNull(result);

        verify(orderItemRepository, times(1)).findByOrderIdAndOrderItemId(eq(orderId), eq(orderItemId));
        verify(orderItemMapper, times(1)).toDto(any(OrderItem.class));
        verifyNoMoreInteractions(orderItemRepository);
        verifyNoMoreInteractions(orderItemMapper);
    }
}
