package ecommerce.bookstore.unittest;

import ecommerce.bookstore.config.JwtUtils;
import ecommerce.bookstore.entity.AuthRequest;
import ecommerce.bookstore.entity.AuthResponse;
import ecommerce.bookstore.entity.RegisterRequest;
import ecommerce.bookstore.entity.User;
import ecommerce.bookstore.enums.Role;
import ecommerce.bookstore.restapi.AuthAPI;
import ecommerce.bookstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthAPITest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthAPI authAPI;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        // Create a mock User object that will be returned by the userService.addUser method
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("John Doe");

        // Configure the mock userService to return the mockUser object
        Mockito.when(userService.addUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(mockUser);

        // Mock the jwtUtils.generateToken() method
        Mockito.when(jwtUtils.generateToken(Mockito.any(User.class))).thenReturn("mocked-access-token");
    }

    @Test
    public void testRegister() {

        RegisterRequest request = new RegisterRequest("John Doe", "johndoe", "password", Role.USER, "Street 123", "New York", "USA", "12345", null);
        ResponseEntity<?> responseEntity = authAPI.register(request);
        AuthResponse response = (AuthResponse) responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getUserId());
    }

    @Test
    public void testRegisterWithExistingUser() {
        // Configure the userService.addUser() method to throw an exception when adding a user with an existing username
        Mockito.when(userService.getUser(Mockito.eq("janedoe"))).thenReturn(new User());

        RegisterRequest request = new RegisterRequest("John Doe", "janedoe", "password", Role.USER, "123 Main St", "CA", "USA", "12345", null);

        ResponseEntity<?> responseEntity = authAPI.register(request);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Username already exists", responseEntity.getBody());
    }

    @Test
    public void testLogin() {
        // Add a user to the database before attempting to log in
        User user = userService.addUser("John Doe", "johndoe", "password", "123 Main St", "CA", "USA", "12345");

        // Create a mock authentication object
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(user);

        // Mock the authenticationManager.authenticate() method to return the mock authentication object
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);

        AuthRequest request = new AuthRequest("johndoe", "password");
        ResponseEntity<?> responseEntity = authAPI.login(request);
        AuthResponse response = (AuthResponse) responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertEquals(user.getId(), response.getUserId());
    }

    @Test
    public void testLoginWithWrongCredentials() {
        // Add a user to the database before attempting to log in
        User user = userService.addUser("John Doe", "johndoe", "password", "123 Main St", "CA", "USA", "12345");

        // Mock the authenticationManager.authenticate() method to throw an exception
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        AuthRequest request = new AuthRequest("johndoe", "wrongpassword");
        ResponseEntity<?> responseEntity = authAPI.login(request);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Wrong Credentials", responseEntity.getBody());
    }

    @Test
    public void testLogout() {
        ResponseEntity<?> response = authAPI.logout();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Logout Successful", responseBody.get("message"));
    }
}
