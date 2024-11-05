package org.marouanedbibih.springsecurity.auth;

import org.marouanedbibih.springsecurity.user.dto.UserDTO;
import org.marouanedbibih.springsecurity.utils.MyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<MyResponse> register(@Valid @RequestBody AuthRequest request) {
        // Register the user using AuthService
        UserDTO userDto = authService.register(request);
        logger.info("Register user information {}", userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(MyResponse.builder()
                .data(userDto)
                .message("User registered successfully")
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        // Login the user using AuthService
        AuthResponse authResponse = authService.login(request.getUsername(), request.getPassword());
        logger.info("User auth infos {}", authResponse);
        return ResponseEntity.status(HttpStatus.OK).body(authResponse);

    }
}
