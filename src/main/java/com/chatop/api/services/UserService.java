package com.chatop.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chatop.api.dto.TokenDTO;
import com.chatop.api.dto.UserDTO;
import com.chatop.api.exceptions.NotFoundException;
import com.chatop.api.models.User;
import com.chatop.api.repositories.IUserRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class UserService {

    private final JwtGeneratorService jwtGeneratorService;
    private final IUserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    /**
     * Retrieves a user from the token.
     *
     * @param token The token containing the user's email.
     * @return The user object corresponding to the token.
     * @throws NotFoundException If the user is not found.
     */
    public User getUserFromToken(TokenDTO token) {
        LOGGER.info("Obtention de l'utilisateur a partir du token.");

        String tokenValue = token.getToken();
        String email = jwtGeneratorService.extractSubject(tokenValue);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        LOGGER.info("Utilisateur trouve : {}", user.getEmail());

        return user;
    }

    /**
     * Retrieves a user from the database by their id.
     *
     * @param id The unique identifier of the user.
     * @return The user object corresponding to the provided id.
     * @throws NotFoundException If the user is not found.
     */
    public UserDTO getUserById(Integer id) {
        LOGGER.info("Obtention de l'utilisateur a partir de son id.");
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        UserDTO userDTO = user.getUserDTOFromUser(user);
        return userDTO;
    }
}
