package com.chatop.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.CredentialsDTO;
import com.chatop.api.dto.RegisterUserDTO;
import com.chatop.api.dto.TokenDTO;
import com.chatop.api.dto.UserDTO;
import com.chatop.api.services.AuthentificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthentificationController {

    @Autowired
    private final AuthentificationService authentificationService;

    /**
     * Authenticates a user.
     *
     * @param credentialsDto The credentials of the user to authenticate.
     * @return A TokenDTO object containing the token for the authenticated
     * user.
     */
    @Operation(summary = "Authentification d'un utilisateur", description = "Authentification d'un utilisateur", tags = {"Authentication"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur connecté avec succès.",
                content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TokenDTO.class)))}),
        @ApiResponse(responseCode = "401", description = "Votre email ou mot de passe n'est pas valide. Connexion non autorisée.",
                content = {
                    @Content(mediaType = "application/json")}),})
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid CredentialsDTO credentialsDto) {
        TokenDTO token = authentificationService.login(credentialsDto);
        return ResponseEntity.ok(token);
    }

    /**
     * Registers a new user.
     *
     * @param userData The registration data of the new user.
     * @return A TokenDTO object containing the token for the newly registered
     * user.
     */
    @Operation(summary = "Enregistrement d'un nouvel utilisateur", description = "Enregistrement d'un nouvel utilisateur", tags = {"Authentication"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur enregistré avec succès.",
                content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TokenDTO.class)))}),
        @ApiResponse(responseCode = "400", description = "Contenu du body de la requête non valide.",
                content = {
                    @Content(mediaType = "application/json")}),})
    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody @Valid RegisterUserDTO userData) {
        TokenDTO token = authentificationService.register(userData);
        return ResponseEntity.ok(token);
    }

    /**
     * Retrieves the user data for the authenticated user.
     *
     * @param token The JWT token representing the authenticated user.
     * @return The user data for the authenticated user.
     */
    @Operation(summary = "Obtention des données de l'utilisateur", description = "Obtention des données de l'utilisateur", tags = {"Authentication"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Donnée de l'utilisateur.",
                content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))}),
        @ApiResponse(responseCode = "401", description = "Utilisateur non reconnu.",
                content = {
                    @Content(mediaType = "application/json")}),})
    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(@RequestHeader("Authorization") String token) {

        UserDTO user = authentificationService.me(token);
        return ResponseEntity.ok(user);
    }
}
