package bookstore.controller;

import bookstore.dto.user.UserLoginRequestDto;
import bookstore.dto.user.UserRegistrationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    protected static MockMvc mockMvc;


    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders.
                webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Perform user login successfully")
    @Sql(scripts = "classpath:insertUser.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void login_Success() throws Exception {
        UserLoginRequestDto loginRequest = new UserLoginRequestDto();
        loginRequest.setEmail("user@example.com");
        loginRequest.setPassword("123456");

        mockMvc.perform(
                        post("/api/auth/login")
                                .content(objectMapper.writeValueAsString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Invalid credentials login failure")
    public void invalidCredentialsLogin_Failure() throws Exception {
        UserLoginRequestDto loginRequest = new UserLoginRequestDto();
        loginRequest.setEmail("user@example.com");
        loginRequest.setPassword("12345");

        mockMvc.perform(
                        post("/api/auth/login")
                                .content(objectMapper.writeValueAsString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    @DisplayName("User registration successful")
    @Sql(scripts = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:deleteCartAndItem.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void register_Success() throws Exception {
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest();
        registrationRequest.setFirstName("test_name");
        registrationRequest.setLastName("test_surname");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("123456");
        registrationRequest.setRepeatPassword("123456");

        mockMvc.perform(
                        post("/api/auth/register")
                                .content(objectMapper.writeValueAsString(registrationRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("User registration input mismatch failure")
    @Sql(scripts = "classpath:deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void registerInputMismatch_Failure() throws Exception {
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest();
        registrationRequest.setFirstName("test_name");
        registrationRequest.setLastName("test_surname");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("123456");
        registrationRequest.setRepeatPassword("123455");

        mockMvc.perform(
                        post("/api/auth/register")
                                .content(objectMapper.writeValueAsString(registrationRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andReturn();
    }
}
