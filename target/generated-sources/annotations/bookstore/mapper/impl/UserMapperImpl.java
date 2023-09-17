package bookstore.mapper.impl;

import bookstore.dto.user.UserLoginRequestDto;
import bookstore.dto.user.UserLoginResponseDto;
import bookstore.dto.user.UserRegistrationRequest;
import bookstore.dto.user.UserResponseDto;
import bookstore.mapper.UserMapper;
import bookstore.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-17T21:01:47+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRegistrationRequest userRegistrationRequest) {
        if ( userRegistrationRequest == null ) {
            return null;
        }

        User user = new User();

        if ( userRegistrationRequest.getEmail() != null ) {
            user.setEmail( userRegistrationRequest.getEmail() );
        }
        if ( userRegistrationRequest.getPassword() != null ) {
            user.setPassword( userRegistrationRequest.getPassword() );
        }
        if ( userRegistrationRequest.getFirstName() != null ) {
            user.setFirstName( userRegistrationRequest.getFirstName() );
        }
        if ( userRegistrationRequest.getLastName() != null ) {
            user.setLastName( userRegistrationRequest.getLastName() );
        }

        return user;
    }

    @Override
    public User toUser(UserLoginRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        if ( dto.getEmail() != null ) {
            user.setEmail( dto.getEmail() );
        }
        if ( dto.getPassword() != null ) {
            user.setPassword( dto.getPassword() );
        }

        return user;
    }

    @Override
    public UserResponseDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        if ( user.getId() != null ) {
            userResponseDto.setId( user.getId() );
        }
        if ( user.getEmail() != null ) {
            userResponseDto.setEmail( user.getEmail() );
        }
        if ( user.getFirstName() != null ) {
            userResponseDto.setFirstName( user.getFirstName() );
        }
        if ( user.getLastName() != null ) {
            userResponseDto.setLastName( user.getLastName() );
        }
        if ( user.getShippingAddress() != null ) {
            userResponseDto.setShippingAddress( user.getShippingAddress() );
        }

        return userResponseDto;
    }

    @Override
    public UserLoginResponseDto toUserLoginResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();

        return userLoginResponseDto;
    }
}
