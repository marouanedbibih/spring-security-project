package org.marouanedbibih.springsecurity.utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class MyDTO {
    
    protected Long id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
