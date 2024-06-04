package com.chatop.api.dto;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalRequestDTO {

    @NotNull(message = "Name cannot be empty.")
    @Schema(type = "string", example = "House near the sea for your vacation", description = "Name of the rental")
    private String name;

    @NotNull(message = "Surface cannot be empty.")
    @Schema(type = "double", example = "150", description = "Surface of the rental")
    private Double surface;

    @NotNull(message = "Price cannot be empty.")
    @Schema(type = "double", example = "800", description = "Price of the rental")
    private Double price;

    private MultipartFile picture;

    @NotNull(message = "Description cannot be empty.")
    @Schema(type = "string", example = "Big house near the sea for 6 persons.", description = "Description of the rental")
    private String description;
}
