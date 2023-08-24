package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.UserLoginRequestDto;
import bookstore.dto.UserLoginResponseDto;
import bookstore.dto.UserRegistrationRequest;
import bookstore.dto.UserResponseDto;
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
