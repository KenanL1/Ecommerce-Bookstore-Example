package ecommerce.bookstore.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecommerce.bookstore.config.JwtUtils;
import ecommerce.bookstore.entity.AuthRequest;
import ecommerce.bookstore.entity.RegisterRequest;
import ecommerce.bookstore.entity.User;
import ecommerce.bookstore.enums.Role;
import ecommerce.bookstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void testRegister() throws Exception {
        RegisterRequest request = new RegisterRequest("John Doe", "johndoe1", "password", Role.USER, "Street 123", "New York", "USA", "12345", null);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isString())
                .andExpect(jsonPath("$.userId").isNumber());
    }

    @Test
    public void testRegisterWithExistingUser() throws Exception {
        // Add a user with the same username before attempting to register with that username
        userService.addUser("Jane Doe", "janedoe1", "password", "456 Elm St", "TX", "USA", "54321");

        RegisterRequest request = new RegisterRequest("John Doe", "janedoe1", "password", Role.USER, "123 Main St", "CA", "USA", "12345", null);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)));

        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().string("Username already exists"));
    }

    @Test
    public void testLogin() throws Exception {
        // Add a user to the database before attempting to log in
        User user = userService.addUser("Bob Doe", "bobdoe1", "password", "123 Main St", "CA", "USA", "12345");

        AuthRequest request = new AuthRequest("bobdoe1", "password");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isString())
                .andExpect(jsonPath("$.userId").value(user.getId()));
    }

    @Test
    public void testLoginWithWrongCredentials() throws Exception {
        // Add a user to the database before attempting to log in
        userService.addUser("Nick Doe", "nickdoe1", "password", "123 Main St", "CA", "USA", "12345");

        AuthRequest request = new AuthRequest("nickdoe1", "wrongpassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Wrong Credentials"));
    }

    @Test
    public void testLogout() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/logout"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Logout Successful"));
    }
}