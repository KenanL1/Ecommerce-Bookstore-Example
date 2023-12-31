package ecommerce.bookstore.restapi;

import ecommerce.bookstore.config.JwtUtils;
import ecommerce.bookstore.entity.AuthRequest;
import ecommerce.bookstore.entity.AuthResponse;
import ecommerce.bookstore.entity.RegisterRequest;
import ecommerce.bookstore.entity.User;
import ecommerce.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthAPI {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userService.getUser(request.getUsername()) != null) {
            // Username already exists, return an error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists");
        }
        User user = userService.addUser(request.getName(), request.getUsername(), request.getPassword(), request.getStreet(), request.getProvince(), request.getCountry(), request.getZip());

        // Generate a JWT token for the user
        String token = jwtUtils.generateToken(user);
        AuthResponse response = new AuthResponse(token, user.getId());
        return ResponseEntity.ok(response);

    }

    // Authenticate a user and generate a JWT token
    @PostMapping("/login")
    public ResponseEntity<?> login(@org.jetbrains.annotations.NotNull @RequestBody AuthRequest request) {
        try {
            // Use Spring Security's authentication manager to authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword())
            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();

            // Generate a JWT token for the user
            String token = jwtUtils.generateToken(user);
            AuthResponse response = new AuthResponse(token, user.getId());

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            // Return an error response if authentication fails
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong Credentials");
        }
    }

    // Log out the current user
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Clear authentication information
        SecurityContextHolder.clearContext();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Logout Successful");
        return ResponseEntity.ok(response);
    }
}
