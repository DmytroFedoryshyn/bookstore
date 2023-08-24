package bookstore.dto;

import bookstore.validation.PasswordsMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordsMatch
public class UserRegistrationRequest {
    @Email
    @Size(min = 4, max = 50)
    private String email;
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
    @NotBlank
    @Size(min = 6, max = 100)
    private String repeatPassword;
    @NotBlank
    @Size(min = 6, max = 100)
    private String firstName;
    @NotBlank
    @Size(min = 6, max = 100)
    private String lastName;
}
