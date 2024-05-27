package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    @Schema(type = "string", example = "Hello, I have a question about your rentals.", description = "The message for the user.")
    @NotNull(message = "Message cannot be empty")
    @Size(max = 2000, message = "Message length must be less than 2000 characters.")
    private String message;

    @Schema(type = "integer", example = "1", description = "The user_id is the id of the user you want to send message.")
    @JsonProperty(value = "user_id")
    @NotNull(message = "user_id cannot be empty")
    private Integer userId;

    @Schema(type = "integer", example = "1", description = "The rental_id is the id of the rental.")
    @JsonProperty(value = "rental_id")
    @NotNull(message = "rental_id cannot be empty")
    private Integer rentalId;
}
