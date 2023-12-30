package bookstore.service;

import bookstore.dto.user.UserRegistrationRequest;
import bookstore.exception.RegistrationException;
import bookstore.mapper.UserMapper;
import bookstore.model.Role;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.shoppingcart.ShoppingCartRepository;
import bookstore.repository.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository repository;
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Register user successfully")
    @Sql(value = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void registerUser_Success() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("new_user1@example.com");
        request.setFirstName("John");
        request.setLastName("Smith");
        request.setPassword("12345");
        request.setRepeatPassword("12345");

        Mockito.when(repository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        Mockito.when(roleService.getByName(Role.RoleName.USER)).thenReturn(new Role());

        userService.registerUser(request);

        Mockito.verify(repository, Mockito.times(1)).findByEmail(request.getEmail());
    }

    @Test
    @DisplayName("Register user with duplicate email failure")
    @Sql(value = "classpath:insertUser.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void registerUserWithDuplicateEmail_Failure() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("user@example.com");
        request.setFirstName("John");
        request.setLastName("Smith");
        request.setPassword("12345");
        request.setRepeatPassword("12345");

        Mockito.when(repository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));

        Assertions.assertThrows(RegistrationException.class, () -> {
            userService.registerUser(request);
        });

        Mockito.verify(repository, Mockito.times(1)).findByEmail(request.getEmail());
        Mockito.verify(repository, Mockito.never()).save(Mockito.any(User.class));

        Mockito.verify(shoppingCartRepository, Mockito.never()).save(Mockito.any(ShoppingCart.class));
    }
}
