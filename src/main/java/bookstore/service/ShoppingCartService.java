package bookstore.service;

import bookstore.dto.cart.AddToCartDto;
import bookstore.dto.cart.CartResponseDto;
import bookstore.dto.cartitem.CartItemFullInfoResponseDto;
import bookstore.dto.cartitem.CartItemResponseDto;

public interface ShoppingCartService {
    CartResponseDto getShoppingCartByUsername(String username);

    void addItemToCart(AddToCartDto dto, String username);

    CartItemResponseDto updateCartItem(Long id, AddToCartDto dto);

    void deleteCartItem(Long id);

    CartItemFullInfoResponseDto getCartItemById(Long id);
}
