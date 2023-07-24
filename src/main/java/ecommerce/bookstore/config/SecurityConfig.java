package ecommerce.bookstore.config;

import ecommerce.bookstore.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                // Enable CORS and disable CSRF
                        csrf().disable()
                // Set permissions on endpoints
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**", "/api/v1/book/**", "/api/v1/review/**", "/api/v1/cart/**", "favicon.ico", "js/*", "/login", "/signup", "/cart", "/checkout", "/book/**", "/")
                .permitAll() //The request requires no autorization and is a public endpoint; note that in this case, the Authentication is never retrieved from the session
                .requestMatchers("/admin").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated()
//                .anyRequest().permitAll()
                .and()
                // Set session management to stateless
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout").logoutSuccessUrl("/").permitAll()
                .and()
                // Add JWT token filter
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
