package com.chatop.api.controllers;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.MessageDTO;
import com.chatop.api.dto.TokenDTO;
import com.chatop.api.services.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

     @Operation(summary = "Create a message", description = "Create a message for a rental.", tags={"Message"})
            @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Message created.",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "400", description = "Message, rental or user cannot be empty. BAD_REQUEST", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Invalid user. UNAUTHORIZED", content = @Content)
            })
    @SecurityRequirement(name = "Bearer Authentication Required")
    @PostMapping("/") 
    public ResponseEntity<?> createRental(@Valid @RequestBody MessageDTO messageDTO, @RequestHeader("Authorization") String token) {
        TokenDTO tokenDTO = new TokenDTO(token.replace("Bearer ", ""));
        messageService.createMessage(messageDTO, tokenDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "Message send with success !"));
    }
}
