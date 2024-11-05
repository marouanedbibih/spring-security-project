package org.marouanedbibih.springsecurity.account;

import lombok.Builder;

@Builder
public record PasswordRequest(
    String password
) {
    
}
