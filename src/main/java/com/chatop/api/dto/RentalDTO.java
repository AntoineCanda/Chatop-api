package com.chatop.api.dto;

import java.time.LocalDateTime;

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
    @NotNull
    @Schema(type = "integer", example = "10", description = "Rental identifier")
    private int id;

    @Schema(type = "string", example = "House near the sea for your vacation", description = "Name of the rental")
    String name;

    @Schema(type = "double", example = "150", description = "Surface of the rental")
    Double surface;

    @Schema(type = "double", example = "800", description = "Price of the rental")
    Double price;

    @Schema(type = "link", example = "https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg", description = "Link of a picture of the rental")
    String picture;

    @Schema(type = "string", example = "Big house near the sea for 6 persons.", description = "Description of the rental")
    String description;

    @Schema(type = "integer", example = "1", description = "Id of the rental's owner")
    Integer ownerId;

    @Schema(type = "date", example = "2023-06-01T00:00:00", description = "Creation date of the offer")
    LocalDateTime createdAt;

    @Schema(type = "date", example = "2024-05-01T00:00:00", description = "Last update date of the offer")
    LocalDateTime updatedAt;
}
