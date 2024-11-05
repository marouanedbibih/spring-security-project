package org.marouanedbibih.springsecurity.account;

import lombok.Builder;

@Builder
public record AccountRequest(
    String username
) {
    
}
