package com.chatop.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalDTO {

    @Id
    @NotNull(message = "id cannot be empty")
    @Schema(type = "integer", example = "10", description = "Rental identifier")
    private int id;

    @NotNull(message = "name cannot be empty")
    @Schema(type = "string", example = "House near the sea for your vacation", description = "Name of the rental")
    String name;

    @NotNull(message = "surface cannot be empty")
    @Schema(type = "double", example = "150", description = "Surface of the rental")
    Double surface;

    @NotNull(message = "price cannot be empty")
    @Schema(type = "double", example = "800", description = "Price of the rental")
    Double price;

    @Schema(type = "link", example = "https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg", description = "Link of a picture of the rental")
    String picture;

    @Schema(type = "string", example = "Big house near the sea for 6 persons.", description = "Description of the rental")
    String description;

    @Schema(type = "integer", example = "1", description = "Id of the rental's owner")
    @JsonProperty("owner_id")
    Integer ownerId;

    @Schema(type = "date", example = "2023-06-01T00:00:00", description = "Creation date of the offer")
    @JsonProperty("created_at")
    LocalDateTime createdAt;

    @Schema(type = "date", example = "2024-05-01T00:00:00", description = "Last update date of the offer")
    @JsonProperty("updated_at")
    LocalDateTime updatedAt;
}
