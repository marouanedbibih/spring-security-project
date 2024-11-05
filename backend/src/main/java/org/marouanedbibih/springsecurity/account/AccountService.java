package org.marouanedbibih.springsecurity.account;

import org.marouanedbibih.springsecurity.exception.MyAuthException;
import org.marouanedbibih.springsecurity.exception.MyNotFoundException;
import org.marouanedbibih.springsecurity.user.User;
import org.marouanedbibih.springsecurity.user.UserMapper;
import org.marouanedbibih.springsecurity.user.UserRepository;
import org.marouanedbibih.springsecurity.user.dto.UserDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO updateUserInfo(Long userId, AccountRequest request) {
        Long authenticatedUserId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getId();

        if (!authenticatedUserId.equals(userId)) {
            throw new MyAuthException("You are not authorized to update this user's information");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MyNotFoundException("User not found"));

        // Update user fields from userDTO as needed
        user.setUsername(request.username());
        // Update other fields as necessary

        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    public UserDTO updatePassword(Long userId, PasswordRequest request) {
        Long authenticatedUserId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getId();

        if (!authenticatedUserId.equals(userId)) {
            throw new MyAuthException("You are not authorized to update this user's password", "authorization");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MyNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(request.password()));
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }
}
