package bookstore.service;

import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.OrderResponseDto;
import bookstore.dto.order.OrderUpdateDto;
import bookstore.mapper.OrderMapper;
import bookstore.model.Book;
import bookstore.model.Order;
import bookstore.model.OrderItem;
import bookstore.model.User;
import bookstore.repository.book.BookRepository;
import bookstore.repository.order.OrderRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final BookRepository bookRepository;

    @Override
    public OrderResponseDto save(OrderRequestDto dto, User user) {
        Order order = orderMapper.toEntity(dto);
        order.setStatus(Order.Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);

        Set<OrderItem> orderItems = order.getOrderItems();
        Iterator<OrderItem> iterator = orderItems.iterator();

        //?
        while (iterator.hasNext()) {
            OrderItem orderItem = iterator.next();
            orderItem.setOrder(order);
            Book book = bookRepository.getReferenceById(orderItem.getBook().getId());
            orderItem.setBook(book);
            orderItem.setPrice(orderItem.getBook().getPrice());
        }

        //?
        order.setTotal(order.getOrderItems().stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> getByUser(User user, Pageable pageable) {
        return orderRepository.getAllByUser(user, pageable)
                .stream()
            .map(orderMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto update(OrderUpdateDto dto, Long id) {
        Order order = orderRepository.getReferenceById(id);
        order.setStatus(dto.getStatus());
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }
}
