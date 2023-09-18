package bookstore.service;

import bookstore.dto.cart.AddToCartDto;
import bookstore.dto.cart.CartResponseDto;
import bookstore.dto.cartitem.CartItemResponseDto;
import bookstore.model.User;

public interface ShoppingCartService {
    CartResponseDto getShoppingCartByUser(User user);

    void addItemToCart(AddToCartDto dto, User user);

    CartItemResponseDto updateCartItem(Long id, AddToCartDto dto);

    void deleteCartItem(Long id);
}
