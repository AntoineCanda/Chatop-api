package com.chatop.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.UserDTO;
import com.chatop.api.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * Get user by id. This method returns details of a user by their id.
     *
     * @param id The unique identifier of the user.
     * @return A {@link ResponseEntity} containing the details of the user.
     */
    @Operation(summary = "Get user by id", description = "Return details of user by id.", tags = {"User"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Details of the user by id.",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
        @ApiResponse(responseCode = "401", description = "Invalid user. UNAUTHORIZED",
                content = @Content)
    })
    @SecurityRequirement(name = "Bearer token")
    @GetMapping("/{id}") // Maps HTTP GET requests to /api/user/{id} to this method
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        // Calls the userService to find a user by their ID and return their details
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
