package com.chatop.api.services;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatop.api.dto.MessageDTO;
import com.chatop.api.dto.TokenDTO;
import com.chatop.api.exceptions.DatabaseException;
import com.chatop.api.models.Message;
import com.chatop.api.repositories.IMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final IMessageRepository messageRepository;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationService.class);

    /**
     * Creates a new message.
     *
     * @param messageDTO The message data transfer object containing the message
     * content and rental ID.
     * @param tokenDTO The token data transfer object containing the user's
     * token.
     *
     * @throws DatabaseException If an error occurs while saving the message to
     * the database.
     */
    @Transactional
    public void createMessage(MessageDTO messageDTO, TokenDTO tokenDTO) {
        LOGGER.info("Debut de la creation d'un nouveau message");

        Message message = Message.builder()
                .rental_id(messageDTO.getRentalId())
                .user_id(userService.getUserFromToken(tokenDTO).getId())
                .message(messageDTO.getMessage()) // Sets the message content from the DTO.
                .created_at(new Timestamp(System.currentTimeMillis())) // Sets the current timestamp as the creation time.
                .build();
        try {
            messageRepository.save(message);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
