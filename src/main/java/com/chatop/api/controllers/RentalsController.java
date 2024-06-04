package com.chatop.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.RentalDTO;
import com.chatop.api.dto.RentalRequestDTO;
import com.chatop.api.dto.RentalsDTO;
import com.chatop.api.dto.ResponseDTO;
import com.chatop.api.dto.TokenDTO;
import com.chatop.api.services.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalsController {

    private final RentalService rentalService;

    /**
     * Find all rentals.
     *
     * @return a ResponseEntity containing a RentalsDTO object with all the
     * rentals.
     */
    @Operation(summary = "Find all rentals", tags = {"Rentals"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rentals retrieved",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalsDTO.class))}),
        @ApiResponse(responseCode = "401", description = "Access denied because the user is invalid. UNAUTHORIZED",
                content = @Content)})
    @SecurityRequirement(name = "Bearer token")
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<RentalsDTO> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    /**
     * Get rental by id. This method retrieves a rental by its unique id.
     *
     * @param id The unique identifier of the rental to be retrieved.
     * @return A ResponseEntity containing a RentalDTO object representing the
     * retrieved rental.
     */
    @Operation(summary = "Get rental by id", description = "Return a rental by id.", tags = {"Rentals"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental found.",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalDTO.class))}),
        @ApiResponse(responseCode = "401", description = "Access denied because the user is invalid. UNAUTHORIZED",
                content = @Content)
    })
    @SecurityRequirement(name = "Bearer token")
    @GetMapping(value = "/{id}") // Maps GET requests with an ID to this method
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    /**
     * Creates a new rental.
     *
     * @param token The authorization token for the user.
     * @param rentalRequest The data containing the details of the new rental.
     * @return A ResponseEntity containing a ResponseDTO object indicating the
     * success of the operation.
     */
    @Operation(summary = "Create a new rental", tags = {"Rentals"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental created",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDTO.class))}),
        @ApiResponse(responseCode = "401", description = "Access denied because the user is invalid. UNAUTHORIZED",
                content = @Content)})
    @SecurityRequirement(name = "Bearer token")
    @PostMapping(value = "")
    public ResponseEntity<ResponseDTO> createRental(@RequestHeader("Authorization") String token, @ModelAttribute RentalRequestDTO rentalRequest) {
        TokenDTO tokenDTO = new TokenDTO(token.replace("Bearer ", ""));

        return ResponseEntity.ok(rentalService.createRental(tokenDTO, rentalRequest));
    }

    /**
     * Updates an existing rental.
     *
     * @param id The unique identifier of the rental to be updated.
     * @param rentalRequest The data containing the details of the updated
     * rental.
     *
     * @return A ResponseEntity containing a ResponseDTO object indicating the
     * success of the operation.
     */
    @Operation(summary = "Update rental", description = "Updating an existing rental.", tags = {"Rentals"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental updated.",
                content = {
                    @Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "401", description = "Invalid user. UNAUTHORIZED",
                content = @Content)
    })
    @SecurityRequirement(name = "Bearer token")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO> updateRental(@PathVariable("id") Integer id, @ModelAttribute RentalRequestDTO rentalRequest) {
        return ResponseEntity.ok(rentalService.updateRental(id, rentalRequest));
    }
}
