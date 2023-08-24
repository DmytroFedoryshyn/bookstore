package bookstore.service;

import bookstore.dto.UserRegistrationRequest;
import bookstore.dto.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequest request);
}
