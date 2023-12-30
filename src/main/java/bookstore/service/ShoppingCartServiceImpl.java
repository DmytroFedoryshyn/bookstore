package bookstore.service;

import bookstore.dto.cart.AddToCartDto;
import bookstore.dto.cart.CartResponseDto;
import bookstore.dto.cartitem.CartItemFullInfoResponseDto;
import bookstore.dto.cartitem.CartItemResponseDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.CartMapper;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.repository.cartitem.CartItemRepository;
import bookstore.repository.shoppingcart.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository repository;
    private final CartMapper cartMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartResponseDto getShoppingCartByUsername(String username) {
        ShoppingCart cart = repository.getByUserEmail(username);
        return cartMapper.toDto(cart);
    }

    @Override
    public void addItemToCart(AddToCartDto dto, String username) {
        CartItem cartItem = cartMapper.toCartItem(dto);
        ShoppingCart cart = repository.getByUserEmail(username);
        cartItem.setShoppingCart(cart);
        cart.getItems().add(cartItem);
        repository.save(cart);
    }

    @Override
    public CartItemResponseDto updateCartItem(Long id, AddToCartDto dto) {
        CartItem cartItem = cartItemRepository.getCartItemById(id).orElseThrow(
                () -> new EntityNotFoundException("Cart item with id " + id + " not found"));
        cartItem.setQuantity(dto.getQuantity());
        return cartMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItemFullInfoResponseDto getCartItemById(Long id) {
        return cartMapper.toFullInfoDto(cartItemRepository.getCartItemById(id).orElseThrow(
                () -> new EntityNotFoundException("Cart item with id " + id + " not found")));
    }
}
