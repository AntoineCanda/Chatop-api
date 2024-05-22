package com.chatop.api.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    @Schema(type = "string", example = "Rentals created", description = "Message sent by the API after performing an operation like a creation.")
    private String message;
    @Schema(type = "string", example = "Ok", description = "Status code of the response.")
    private HttpStatus status;
    @Schema(type = "array", example="User with this email already exists", description = "List of errors that occurred during the operation.")
    private List<String> errors;
}
