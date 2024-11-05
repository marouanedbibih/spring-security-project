package org.marouanedbibih.springsecurity.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UpdateUserRequest(
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    String username,

    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role must be either ADMIN or USER")
    String role,

    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).*$",
        message = "Password must contain at least one digit, one letter, and one special character"
    )
    String password
) {

}
