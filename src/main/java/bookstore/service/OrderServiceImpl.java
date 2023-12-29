package bookstore.service;

import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.OrderResponseDto;
import bookstore.dto.order.OrderUpdateDto;
import bookstore.mapper.OrderMapper;
import bookstore.model.Book;
import bookstore.model.Order;
import bookstore.model.OrderItem;
import bookstore.repository.book.BookRepository;
import bookstore.repository.order.OrderRepository;
import bookstore.repository.user.UserRepository;
import bookstore.util.SortParametersParsingUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final BookRepository bookRepository;
    private final SortParametersParsingUtil sortParametersParsingUtil;
    private final UserRepository userRepository;

    @Override
    public OrderResponseDto save(OrderRequestDto dto, String username) {
        Order order = orderMapper.toEntity(dto);
        order.setStatus(Order.Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setUser(userRepository.findByEmail(username).get());

        Set<OrderItem> orderItems = order.getOrderItems();
        
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
            Book book = bookRepository.getReferenceById(orderItem.getBook().getId());
            orderItem.setBook(book);
            orderItem.setPrice(orderItem.getBook().getPrice());
        }

        order.setTotal(order.getOrderItems().stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> getByUser(String username, int page, int size, String sort) {
        Sort.Order sortOrder = sortParametersParsingUtil.parseSortOrder(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));

        return orderRepository.getAllByUser_Email(username, pageable)
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
