package bookstore.service;

import bookstore.dto.user.UserRegistrationRequest;
import bookstore.dto.user.UserResponseDto;
import bookstore.exception.RegistrationException;
import bookstore.mapper.UserMapper;
import bookstore.model.Role;
import bookstore.model.User;
import bookstore.repository.user.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto registerUser(UserRegistrationRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("Unable to complete registration.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRoles(Set.of(roleService.getByName(Role.RoleName.USER)));
        user.setPassword(passwordEncoder.encode(request.getPassword()));;
        User savedUser = repository.save(user);

        return userMapper.toUserDto(savedUser);
    }
}
