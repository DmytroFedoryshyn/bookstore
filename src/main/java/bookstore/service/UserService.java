package bookstore.service;

import bookstore.dto.user.UserRegistrationRequest;
import bookstore.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequest request);
}
