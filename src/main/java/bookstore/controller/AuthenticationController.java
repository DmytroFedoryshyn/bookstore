package bookstore.controller;

import bookstore.dto.user.UserLoginRequestDto;
import bookstore.dto.user.UserLoginResponseDto;
import bookstore.dto.user.UserRegistrationRequest;
import bookstore.dto.user.UserResponseDto;
import bookstore.exception.RegistrationException;
import bookstore.security.AuthenticationService;
import bookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Endpoints for managing authentication")

public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Operation(summary = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully logged in",
                    content = @Content(schema = @Schema(implementation
                            = UserLoginResponseDto.class),
                    examples = @ExampleObject(value = "{\n"
                            + "    \"token\": \"eyJhbGciOiJIUzM4NCJ9."
                            + "eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImlhdCI6MTY5MjcxOTMwNiwiZXhw"
                            + "IjoxNjkyNzE5NjA2fQ.imts77_K_HCZKuzYU-RK1mQAOlBkncgKvmKoMAOs"
                            + "Vbf-urbfar4KWLIRkUyy3akG\"\n"
                            + "}"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/login")
    public UserLoginResponseDto login(@Valid @RequestBody UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @Operation(summary = "User registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class),
                    examples = @ExampleObject(value = "{\n"
                            + "    \"id\": 65,\n"
                            + "    \"email\": \"admin1@example.com\",\n"
                            + "    \"firstName\": \"John\",\n"
                            + "    \"lastName\": \"Smith\",\n"
                            + "    \"shippingAddress\": null\n"
                            + "}"))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })

    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequest request)
            throws RegistrationException {
        return userService.registerUser(request);
    }
}

