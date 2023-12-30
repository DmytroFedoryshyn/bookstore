package bookstore.service;

import bookstore.dto.cart.AddToCartDto;
import bookstore.dto.cart.CartResponseDto;
import bookstore.dto.cartitem.CartItemFullInfoResponseDto;
import bookstore.dto.cartitem.CartItemResponseDto;
import bookstore.mapper.CartMapper;
import bookstore.model.Book;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.cartitem.CartItemRepository;
import bookstore.repository.shoppingcart.ShoppingCartRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {
    @Mock
    private CartMapper cartMapper;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Test
    @DisplayName("Retrieve a shopping cart by user successfully")
    @Sql(value = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertUser.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertCartAndItem.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteCartAndItem.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getShoppingCartByUser_Success() {
        User user = new User();
        user.setId(2L);
        user.setEmail("user@example.com");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);

        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setUserId(user.getId());

        Mockito.when(shoppingCartRepository.getByUserEmail(user.getUsername())).thenReturn(shoppingCart);
        Mockito.when(cartMapper.toDto(shoppingCart)).thenReturn(cartResponseDto);

        Assertions.assertNotNull(shoppingCartService.getShoppingCartByUsername(user.getUsername()));
    }

    @Test
    @DisplayName("Add item to cart successfully")

    public void addItemToCart_Success() {
        User user = new User();
        user.setId(2L);
        user.setEmail("user@example.com");

        AddToCartDto addToCartDto = new AddToCartDto();
        addToCartDto.setBookId(1L);
        addToCartDto.setQuantity(1);



        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(1);
        Book book = new Book();
        book.setId(1L);
        cartItem.setBook(book);

        Mockito.when(shoppingCartRepository.getByUserEmail(user.getUsername())).thenReturn(shoppingCart);
        Mockito.when(cartMapper.toCartItem(addToCartDto)).thenReturn(cartItem);

        shoppingCartService.addItemToCart(addToCartDto, user.getUsername());

        Mockito.verify(shoppingCartRepository, Mockito.times(1)).getByUserEmail(user.getUsername());

        Mockito.verify(shoppingCartRepository, Mockito.times(1)).save(shoppingCart);
    }

    @Test
    @DisplayName("Update cart item quantity successfully")
    @Sql(value = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertUser.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertCartAndItem.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteCartAndItem.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateCartItem_Success() {
        User user = new User();
        user.setId(1L);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);

        Book book = new Book();
        book.setId(1L);

        Long cartItemId = 1L;
        AddToCartDto updatedDto = new AddToCartDto();
        updatedDto.setBookId(book.getId());
        updatedDto.setQuantity(5);

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        cartItem.setId(cartItemId);
        cartItem.setQuantity(2);

        Mockito.when(cartItemRepository.getCartItemById(cartItemId)).thenReturn(Optional.of(cartItem));
        Mockito.when(cartItemRepository.save(Mockito.any(CartItem.class))).thenReturn(cartItem);

        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setId(1L);
        cartItemResponseDto.setBookId(book.getId());
        cartItemResponseDto.setQuantity(5);
        cartItemResponseDto.setBookTitle(book.getTitle());

        Mockito.when(cartMapper.toDto(cartItem)).thenReturn(cartItemResponseDto);

        CartItemResponseDto updatedResponse = shoppingCartService.updateCartItem(cartItemId, updatedDto);

        Mockito.verify(cartItemRepository, Mockito.times(1)).getCartItemById(cartItemId);

        Mockito.verify(cartItemRepository, Mockito.times(1)).save(Mockito.any(CartItem.class));

        Assertions.assertNotNull(updatedResponse);
        Assertions.assertEquals(cartItemId, updatedResponse.getId());
        Assertions.assertEquals(updatedDto.getQuantity(), updatedResponse.getQuantity());
    }

    @Test
    @DisplayName("Delete cart item successfully")
    public void deleteCartItem_Success() {
        Long cartItemId = 1L;
        shoppingCartService.deleteCartItem(cartItemId);

        Mockito.verify(cartItemRepository, Mockito.times(1)).deleteById(cartItemId);
    }


    @Test
    @DisplayName("Get detailed information of cart item by ID successfully")
    @Sql(value = "classpath:insertBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertUser.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:insertCartAndItem.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:deleteFromBooksAndCategory.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteCartAndItem.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCartItemById_Success() {
        Long cartItemId = 1L;

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        cartItem.setShoppingCart(shoppingCart);
        Book book = new Book();
        book.setId(1L);
        book.setTitle("title");

        cartItem.setBook(book);

        CartItemFullInfoResponseDto responseDto = new CartItemFullInfoResponseDto();
        responseDto.setShoppingCartId(shoppingCart.getId());
        responseDto.setBookId(book.getId());
        responseDto.setBookTitle(book.getTitle());

        Mockito.when(cartItemRepository.getCartItemById(cartItemId)).thenReturn(Optional.of(cartItem));
        Mockito.when(cartMapper.toFullInfoDto(cartItem)).thenReturn(responseDto);

        CartItemFullInfoResponseDto result = shoppingCartService.getCartItemById(cartItemId);

        Assertions.assertNotNull(result);
    }
}
