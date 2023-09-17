package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.cart.AddToCartDto;
import bookstore.dto.cart.CartResponseDto;
import bookstore.dto.cartItem.CartItemResponseDto;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)

public interface CartMapper {
    @Mapping(source = "user.id", target = "userId")
    CartResponseDto toDto(ShoppingCart shoppingCart);
    @Mapping(source = "bookId", target = "book.id")
    CartItem toCartItem(AddToCartDto dto);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemResponseDto toDto(CartItem item);
}
