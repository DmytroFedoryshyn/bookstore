package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.user.UserLoginRequestDto;
import bookstore.dto.user.UserLoginResponseDto;
import bookstore.dto.user.UserRegistrationRequest;
import bookstore.dto.user.UserResponseDto;
import bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toUser(UserRegistrationRequest userRegistrationRequest);

    @Mapping(target = "id", ignore = true)
    User toUser(UserLoginRequestDto dto);

    UserResponseDto toUserDto(User user);

    UserLoginResponseDto toUserLoginResponseDto(User user);
}
