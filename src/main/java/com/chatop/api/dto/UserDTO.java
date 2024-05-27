package com.chatop.api.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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

    @Schema(type = "string", example = "Test", description = "Name of the user")
    @NotNull(message = "Name cannot be empty")
    private String name;

    @Schema(type = "string", example = "test@test.com", description = "Email of the user")
    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    @Schema(type = "string", example = "2021-10-10T10:10:10", description = "Date of creation of the user")
    @NotNull(message = "Date of creation cannot be empty")
    private LocalDateTime created_at;

    @Schema(type = "string", example = "2021-10-10T10:10:10", description = "Date of update of the user")
    @NotNull(message = "Date of update cannot be empty")
    private LocalDateTime updated_at;

}
