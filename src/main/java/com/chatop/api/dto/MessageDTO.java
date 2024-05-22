package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    @NotNull(message = "Message cannot be empty") 
    @Size(max = 2000, message = "Message length must be less than 2000 characters.")
    private String message;

    @JsonProperty(value = "user_id")
    @NotNull 
    private Integer userId; 

    @JsonProperty(value = "rental_id")
    @NotNull 
    private Integer rentalId;
}