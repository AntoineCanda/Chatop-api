package com.chatop.api.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalsDTO {

    @Schema(type = "array", description = "List of rentals")
    List<RentalDTO> rentals;
}
