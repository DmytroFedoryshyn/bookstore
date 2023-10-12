package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.orderitem.OrderItemRequestDto;
import bookstore.dto.orderitem.OrderItemResponseDto;
import bookstore.model.Book;
import bookstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "mapBookFromLong")
    OrderItem toEntity(OrderItemRequestDto orderItemRequestDto);

    @Named("mapBookFromLong")
    default Book mapBookFromLong(Long bookId) {
        Book book = new Book();
        book.setId(bookId);
        return book;
    }
}
