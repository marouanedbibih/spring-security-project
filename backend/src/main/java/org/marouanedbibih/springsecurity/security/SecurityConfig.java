package org.marouanedbibih.springsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String ADMIN = "ADMIN";
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFilter jwtFilter;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(request -> request
                // Public endpoints
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // User management endpoints - accessible only to ADMIN
                .requestMatchers(HttpMethod.POST, "/api/v1/user").hasAuthority(ADMIN)
                .requestMatchers(HttpMethod.PUT, "/api/v1/user/{id}").hasAuthority(ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/v1/user/{id}").hasAuthority(ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority(ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/api/v1/user/{id}").hasAuthority(ADMIN)

                // Other endpoints require authentication
                .anyRequest().authenticated());

        // Set session management to stateless
        http.sessionManagement(
                sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Handle unauthorized requests
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

        // Add the JWT filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication provider bean
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthProvider(customUserDetailsService, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // This manager will use the CustomAuthProvider
        return config.getAuthenticationManager();
    }
}
