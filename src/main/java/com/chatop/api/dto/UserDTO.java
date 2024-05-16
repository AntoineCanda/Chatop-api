package com.chatop.api.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Integer id;

    private String name;

    @Email(message = "Invalid email address")
    private String email;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    
}