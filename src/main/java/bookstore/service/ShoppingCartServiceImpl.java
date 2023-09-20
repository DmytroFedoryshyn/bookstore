package bookstore.service;

import bookstore.dto.cart.AddToCartDto;
import bookstore.dto.cart.CartResponseDto;
import bookstore.dto.cartitem.CartItemFullInfoResponseDto;
import bookstore.dto.cartitem.CartItemResponseDto;
import bookstore.mapper.CartMapper;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.cartitem.CartItemRepository;
import bookstore.repository.shoppingcart.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository repository;
    private final CartMapper cartMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartResponseDto getShoppingCartByUser(User user) {
        ShoppingCart cart = repository.getByUser(user);
        return cartMapper.toDto(cart);
    }

    @Override
    public void addItemToCart(AddToCartDto dto, User user) {
        CartItem cartItem = cartMapper.toCartItem(dto);
        ShoppingCart cart = repository.getByUser(user);
        cart.getItems().add(cartItem);
        repository.save(cart);
    }

    @Override
    public CartItemResponseDto updateCartItem(Long id, AddToCartDto dto) {
        CartItem cartItem = cartItemRepository.getCartItemById(id);
        cartItem.setQuantity(dto.getQuantity());
        return cartMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItemFullInfoResponseDto getCartItemById(Long id) {
        return cartMapper.toFullInfoDto(cartItemRepository.getCartItemById(id));
    }
}
