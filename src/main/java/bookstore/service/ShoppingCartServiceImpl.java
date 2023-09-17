package bookstore.service;

import bookstore.dto.cart.AddToCartDto;
import bookstore.dto.cart.CartResponseDto;
import bookstore.dto.cartItem.CartItemResponseDto;
import bookstore.dto.cartItem.UpdateCartItemDto;
import bookstore.mapper.CartMapper;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.cartItem.CartItemRepository;
import bookstore.repository.shoppingCart.ShoppingCartRepository;
import bookstore.repository.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository repository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartResponseDto getByUser() {
        ShoppingCart cart = getCartByUser();
        return cartMapper.toDto(cart);
    }

    @Override
    public void addItemToCart(AddToCartDto dto) {
        CartItem cartItem = cartMapper.toCartItem(dto);
        ShoppingCart cart = getCartByUser();
        cart.getItems().add(cartItem);
        cartItem.setShoppingCart(cart);
        repository.save(cart);
    }

    @Override
    public CartItemResponseDto update(Long id, UpdateCartItemDto dto) {
        CartItem cartItem = cartItemRepository.getReferenceById(id);
        cartItem.setQuantity(dto.getQuantity());
        return cartMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        } else {
            return "Anonymous";
        }
    }

    private ShoppingCart getCartByUser() {
        String username = getCurrentUsername();
        Optional<User> currentUser = userRepository.findByEmail(username);
        if (currentUser.isEmpty()) {
            throw new UsernameNotFoundException("user with email: " + username + " not found");
        }
        return repository.getByUser(currentUser.get());
    }
}
