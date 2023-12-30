package bookstore.service;

import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.OrderResponseDto;
import bookstore.dto.order.OrderUpdateDto;
import bookstore.mapper.OrderMapper;
import bookstore.model.Order;
import bookstore.model.User;
import bookstore.repository.book.BookRepository;
import bookstore.repository.order.OrderRepository;
import bookstore.repository.user.UserRepository;
import bookstore.util.SortParametersParsingUtil;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private SortParametersParsingUtil sortParametersParsingUtil;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Save user's order successfully")
    public void saveOrder_Success() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();

        when(orderMapper.toEntity(any(OrderRequestDto.class))).thenReturn(new Order());
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());
        when(orderMapper.toDto(any(Order.class))).thenReturn(new OrderResponseDto());
        User user = new User();
        user.setEmail("user@example.com");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        OrderResponseDto result = orderService.save(orderRequestDto, "user@example.com");

        assertNotNull(result);
        Mockito.verify(orderMapper, Mockito.times(1)).toEntity(eq(orderRequestDto));
        Mockito.verify(orderRepository, Mockito.times(1)).save(any(Order.class));
        Mockito.verify(orderMapper, Mockito.times(1)).toDto(any(Order.class));
    }


    @Test
    @DisplayName("Get a list of orders that belong to the user successfully")
    public void getOrders_Success() {
        int page = 0;
        int size = 10;
        String sort = "createdAt,desc";

        when(sortParametersParsingUtil.parseSortOrder(any(String.class))).thenReturn(Sort.Order.desc("createdAt"));
        when(orderRepository.getAllByUserEmail(any(), any(Pageable.class))).thenReturn(List.of(new Order()));
        when(orderMapper.toDto(any(Order.class))).thenReturn(new OrderResponseDto());

        List<OrderResponseDto> result = orderService.getByUser("user@example.com", page, size, sort);

        assertNotNull(result);

        Mockito.verify(sortParametersParsingUtil, Mockito.times(1)).parseSortOrder(eq(sort));
        Mockito.verify(orderRepository, Mockito.times(1)).getAllByUserEmail(any(), any(Pageable.class));
        Mockito.verify(orderMapper, Mockito.times(1)).toDto(any(Order.class));

        Mockito.verify(orderRepository, Mockito.times(1)).getAllByUserEmail(any(), any(Pageable.class));
        Mockito.verifyNoMoreInteractions(orderRepository);
    }


    @Test
    @DisplayName("Update order successfully")
    public void updateOrder_Success() {
        OrderUpdateDto orderUpdateDto = new OrderUpdateDto();
        Long orderId = 1L;

        when(orderRepository.getReferenceById(any(Long.class))).thenReturn(new Order());
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());
        when(orderMapper.toDto(any(Order.class))).thenReturn(new OrderResponseDto());

        OrderResponseDto result = orderService.update(orderUpdateDto, orderId);

        assertNotNull(result);

        Mockito.verify(orderRepository, Mockito.times(1)).getReferenceById(eq(orderId));
        Mockito.verify(orderRepository, Mockito.times(1)).save(any(Order.class));
        Mockito.verify(orderMapper, Mockito.times(1)).toDto(any(Order.class));

        Mockito.verify(orderRepository, Mockito.times(1)).getReferenceById(eq(orderId));
        Mockito.verifyNoMoreInteractions(orderRepository);
    }
}
