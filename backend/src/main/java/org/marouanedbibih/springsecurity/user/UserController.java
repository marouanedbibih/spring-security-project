package org.marouanedbibih.springsecurity.user;

import java.util.List;

import org.marouanedbibih.springsecurity.user.dto.CreateUserRequest;
import org.marouanedbibih.springsecurity.user.dto.UpdateUserRequest;
import org.marouanedbibih.springsecurity.user.dto.UserDTO;
import org.marouanedbibih.springsecurity.utils.MyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

        private final UserServiceImpl userService;

        // Create a new user
        @PostMapping("/api/v1/user")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<MyResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
                UserDTO user = userService.create(request);
                return ResponseEntity.status(HttpStatus.CREATED).body(MyResponse.builder()
                                .data(user)
                                .message("User created successfully")
                                .build());
        }

        // Update an existing user
        @PutMapping("/api/v1/user/{id}")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<MyResponse> updateUser(
                        @PathVariable Long id,
                        @Valid @RequestBody UpdateUserRequest request) {
                UserDTO updatedUser = userService.update(request, id);
                return ResponseEntity.status(HttpStatus.OK).body(MyResponse.builder()
                                .data(updatedUser)
                                .message("User updated successfully")
                                .build());
        }

        // Get a user by ID
        @GetMapping("/api/v1/user/{id}")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<MyResponse> getUserById(@PathVariable Long id) {
                UserDTO user = userService.get(id);
                return ResponseEntity.status(HttpStatus.OK).body(MyResponse.builder()
                                .data(user)
                                .message("User retrieved successfully")
                                .build());
        }

        // Get a list of all users
        @GetMapping("/api/v1/users")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<MyResponse> getAllUsers() {
                List<UserDTO> users = userService.getAll();
                return ResponseEntity.status(HttpStatus.OK).body(MyResponse.builder()
                                .data(users)
                                .message("Users retrieved successfully")
                                .build());
        }

        // Delete a user by ID
        @DeleteMapping("/api/v1/user/{id}")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<MyResponse> deleteUser(@PathVariable Long id) {
                userService.delete(id);
                return ResponseEntity.status(HttpStatus.OK).body(MyResponse.builder()
                                .message("User deleted successfully")
                                .build());
        }
}
