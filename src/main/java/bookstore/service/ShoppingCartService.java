package bookstore.service;

import bookstore.dto.cart.AddToCartDto;
import bookstore.dto.cart.CartResponseDto;
import bookstore.dto.cartItem.CartItemResponseDto;
import bookstore.dto.cartItem.UpdateCartItemDto;

public interface ShoppingCartService {
    CartResponseDto getByUser();

    void addItemToCart(AddToCartDto dto);

    CartItemResponseDto update(Long id, UpdateCartItemDto dto);

    void delete(Long id);
}
