package org.marouanedbibih.springsecurity.auth;

import org.marouanedbibih.springsecurity.exception.AlreadyExistException;
import org.marouanedbibih.springsecurity.jwt.JwtUtils;
import org.marouanedbibih.springsecurity.user.User;
import org.marouanedbibih.springsecurity.user.UserMapper;
import org.marouanedbibih.springsecurity.user.UserRepository;
import org.marouanedbibih.springsecurity.user.UserRole;
import org.marouanedbibih.springsecurity.user.dto.UserDTO;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;

    public UserDTO register(AuthRequest request) {
        // Check if the user already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AlreadyExistException("Username already exists", "username");
        }

        // Create new user
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();
        user = userRepository.save(user);

        // Generate response
        return userMapper.toDto(user);
    }

    public AuthResponse login(String username, String password) throws AuthenticationException {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        // If authentication is successful, retrieve user details
        User user = (User) authentication.getPrincipal();

        // Transform User to UserDTO
        UserDTO userDTO = userMapper.toDto(user);

        // Get the token from the authentication object
        String token = jwtUtils.createToken(userDTO);

        // Return AuthResponse
        return AuthResponse.builder()
                .token(token)
                .user(userDTO)
                .build();

    }
}
