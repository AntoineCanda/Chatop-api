package com.chatop.api.dto;

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
public class CredentialsDTO {

    @Schema(type = "string", example = "test@test.com", description = "Email of the user")
    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    @Schema(type = "string", example = "MyPr1vat3Pa$$w0rd!", description = "The password of the user")
    @NotNull(message = "Password cannot be blank")
    private String password;

}
