package bookstore.service;

import bookstore.dto.UserResponseDto;
import bookstore.mapper.UserMapper;
import bookstore.model.Role;
import bookstore.model.User;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponseDto user = userService.loadByUsername(username);

        org.springframework.security.core.userdetails.User.UserBuilder builder;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.getPassword());
            builder.roles(user.getRoles()
                    .stream()
                    .map(Role::getName)
                    .toArray(String[]::new));
            return builder.build();
        }

        throw new UsernameNotFoundException("User not found.");
    }
}
