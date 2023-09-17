package bookstore.mapper.impl;

import bookstore.dto.cart.AddToCartDto;
import bookstore.dto.cart.CartResponseDto;
import bookstore.dto.cartItem.CartItemResponseDto;
import bookstore.mapper.CartMapper;
import bookstore.model.Book;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-17T21:01:47+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public CartResponseDto toDto(ShoppingCart shoppingCart) {
        if ( shoppingCart == null ) {
            return null;
        }

        CartResponseDto cartResponseDto = new CartResponseDto();

        Long id = shoppingCartUserId( shoppingCart );
        if ( id != null ) {
            cartResponseDto.setUserId( id );
        }
        if ( shoppingCart.getId() != null ) {
            cartResponseDto.setId( shoppingCart.getId() );
        }
        Set<CartItemResponseDto> set = cartItemSetToCartItemResponseDtoSet( shoppingCart.getItems() );
        if ( set != null ) {
            cartResponseDto.setItems( set );
        }

        return cartResponseDto;
    }

    @Override
    public CartItem toCartItem(AddToCartDto dto) {
        if ( dto == null ) {
            return null;
        }

        CartItem cartItem = new CartItem();

        cartItem.setBook( addToCartDtoToBook( dto ) );
        cartItem.setQuantity( dto.getQuantity() );

        return cartItem;
    }

    @Override
    public CartItemResponseDto toDto(CartItem item) {
        if ( item == null ) {
            return null;
        }

        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();

        Long id = itemBookId( item );
        if ( id != null ) {
            cartItemResponseDto.setBookId( id );
        }
        String title = itemBookTitle( item );
        if ( title != null ) {
            cartItemResponseDto.setBookTitle( title );
        }
        if ( item.getId() != null ) {
            cartItemResponseDto.setId( item.getId() );
        }
        cartItemResponseDto.setQuantity( item.getQuantity() );

        return cartItemResponseDto;
    }

    private Long shoppingCartUserId(ShoppingCart shoppingCart) {
        if ( shoppingCart == null ) {
            return null;
        }
        User user = shoppingCart.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Set<CartItemResponseDto> cartItemSetToCartItemResponseDtoSet(Set<CartItem> set) {
        if ( set == null ) {
            return null;
        }

        Set<CartItemResponseDto> set1 = new HashSet<CartItemResponseDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CartItem cartItem : set ) {
            set1.add( toDto( cartItem ) );
        }

        return set1;
    }

    protected Book addToCartDtoToBook(AddToCartDto addToCartDto) {
        if ( addToCartDto == null ) {
            return null;
        }

        Book book = new Book();

        if ( addToCartDto.getBookId() != null ) {
            book.setId( addToCartDto.getBookId() );
        }

        return book;
    }

    private Long itemBookId(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }
        Book book = cartItem.getBook();
        if ( book == null ) {
            return null;
        }
        Long id = book.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String itemBookTitle(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }
        Book book = cartItem.getBook();
        if ( book == null ) {
            return null;
        }
        String title = book.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }
}
