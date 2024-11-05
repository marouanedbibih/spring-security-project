package org.marouanedbibih.springsecurity.user;

import org.marouanedbibih.springsecurity.user.dto.CreateUserRequest;
import org.marouanedbibih.springsecurity.user.dto.UpdateUserRequest;
import org.marouanedbibih.springsecurity.user.dto.UserDTO;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {


    // Entity to DTO
    public UserDTO toDto(User user) {
        if (user == null)
            return null;

        return UserDTO.builder()
                .id(user.getId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    // DTO to Entity
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null)
            return null;

        return User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .build();
    }

    // CreateUserRequest to Entity
    public User toEntity(CreateUserRequest request) {
        if (request == null)
            return null;

        return User.builder()
                .username(request.username())
                .password(request.password())
                .role(UserRole.valueOf(request.role())) // Assuming role is a valid UserRole enum value
                .build();
    }

    // UpdateUserRequest to Entity (partial update)
    public User toEntity(UpdateUserRequest request, User existingUser) {
        if (request == null)
            return existingUser;

        if (request.username() != null) {
            existingUser.setUsername(request.username());
        }
        if (request.password() != null) {
            existingUser.setPassword(request.password());
        }
        if (request.role() != null) {
            existingUser.setRole(UserRole.valueOf(request.role()));
        }
        return existingUser;
    }

    // CreateUserRequest to DTO
    public UserDTO toDto(CreateUserRequest request) {
        if (request == null)
            return null;

        return UserDTO.builder()
                .username(request.username())
                .password(request.password()) // Adjust based on exposure policy
                .role(UserRole.valueOf(request.role()))
                .build();
    }

    // UpdateUserRequest to DTO
    public UserDTO toDto(UpdateUserRequest request) {
        if (request == null)
            return null;

        return UserDTO.builder()
                .username(request.username())
                .password(request.password())
                .role(request.role() != null ? UserRole.valueOf(request.role()) : null)
                .build();
    }

    // List of entities to List of DTOs
    public List<UserDTO> toDtoList(List<User> users) {
        if (users == null)
            return null;

        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // List of DTOs to List of entities
    public List<User> toEntityList(List<UserDTO> userDTOs) {
        if (userDTOs == null)
            return null;

        return userDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }


}
