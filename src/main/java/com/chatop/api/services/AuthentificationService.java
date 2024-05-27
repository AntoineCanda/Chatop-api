package com.chatop.api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.chatop.api.dto.TokenDTO;
import com.chatop.api.dto.UserDTO;
import com.chatop.api.exceptions.AuthentificationException;
import com.chatop.api.models.Role;
import com.chatop.api.models.Token;
import com.chatop.api.models.TokenType;
import com.chatop.api.models.User;
import com.chatop.api.repositories.ITokenRepository;
import com.chatop.api.repositories.IUserRepository;
import com.chatop.api.dto.CredentialsDTO;
import com.chatop.api.dto.RegisterUserDTO;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import com.chatop.api.exceptions.DatabaseException;
import com.chatop.api.exceptions.UserConflictException;

import lombok.AllArgsConstructor;

/**
 *
 * @author Antoine CANDA
 */
@Service
@AllArgsConstructor
public class AuthentificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationService.class);
    private final AuthenticationManager authenticateManager;
    private final PasswordEncoder passwordEncoder;

    private final IUserRepository userRepository;
    private final ITokenRepository tokenRepository;
    private final JwtGeneratorService jwtGeneratorService;
    private final UserService userService;

    /**
     * Authenticates the user and generates a token.
     *
     * @param credentials The credentials of the user to authenticate.
     * @return A tokenDTO object containing the generated token.
     * @throws AuthentificationException If the credentials are invalid.
     */
    @Transactional
    public TokenDTO login(CredentialsDTO credentials) {
        LOGGER.info("Debut de l'authentification de l'utilisateur : {}", credentials.getEmail());

        try {
            // Attempts to authenticate the user using the provided credentials.
            // If the credentials are invalid, a BadCredentialsException is thrown.
            authenticateManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
        } catch (BadCredentialsException e) {
            // Throws an AuthentificationException with a message indicating that the credentials are invalid.
            throw new AuthentificationException("Invalid credentials. Please verify and try again.");
        }

        // Retrieves the user from the database using the provided email.
        // If the user is not found, an exception is thrown.
        User user = userRepository.findByEmail(credentials.getEmail()).orElseThrow();

        LOGGER.info("Utilisateur trouve : {}", user.toString());
        // Disables all valid tokens for the user.
        this.disableAllTokensForUser(user);
        // Generates a token for the authenticated user.
        String token = jwtGeneratorService.generateToken(user);
        // Processes the generated token and returns a TokenDTO object containing the token.
        return this.processToken(user, token);
    }

    /**
     * Registers a new user.
     *
     * @param userData The registration data for the new user.
     * @return A TokenDTO object containing the generated token.
     * @throws UserConflictException If an user already exists with the provided
     * email.
     * @throws DatabaseException If an error occurs while processing the
     * registration.
     */
    @Transactional
    public TokenDTO register(RegisterUserDTO userData) {
        LOGGER.info("Debut de l'inscription de l'utilisateur : {}", userData.getEmail());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (userRepository.findByEmail(userData.getEmail()).isPresent()) {
            throw new UserConflictException("An user already exists with this email.");
        }

        User user = User.builder()
                .name(userData.getName())
                .email(userData.getEmail())
                .password(passwordEncoder.encode(userData.getPassword()))
                .role(Role.USER)
                .active(true)
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();

        try {
            User savedUser = userRepository.save(user);
            LOGGER.info("Utilisateur sauvegarde : {}", savedUser.getEmail());

            String token = jwtGeneratorService.generateToken(savedUser);
            return this.processToken(savedUser, token);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public UserDTO me(String token) {
        LOGGER.info("Recuperation de l'utilisateur a partir du token.");

        token = token.replace("Bearer ", "");
        TokenDTO tokenDTO = new TokenDTO(token);

        User user = userService.getUserFromToken(tokenDTO);
        UserDTO userDTO = user.getUserDTOFromUser(user);

        return userDTO;
    }

    /**
     * Disables all valid tokens for the specified user.
     *
     * @param user The user whose tokens should be disabled.
     * @throws DatabaseException If an error occurs while processing the token
     * disabling.
     */
    private void disableAllTokensForUser(User user) {
        LOGGER.info("Desactivation de tous les tokens valides pour l'utilisateur : {}", user.getEmail());

        try {
            List<Token> tokens = tokenRepository.findAllValidTokenByUser(user.getId());
            tokens.forEach(token -> {
                token.setValid(false);
                token.setExpired(true);
            });

            tokenRepository.saveAll(tokens);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    /**
     * Saves a new token for the specified user.
     *
     * @param user The user whose token should be saved.
     * @param token The token to be saved for the user.
     * @throws DatabaseException If an error occurs while processing the token
     * saving.
     */
    private void saveToken(User user, String token) {
        LOGGER.info("Sauvegarde du nouveau token pour l'utilisateur : {}", user.getEmail());

        Token newToken = new Token();
        newToken.setToken(token);
        newToken.setValid(true);
        newToken.setExpired(false);
        newToken.setUser_id(user.getId());
        newToken.setType(TokenType.BEARER_TOKEN);
        newToken.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        try {
            tokenRepository.save(newToken);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    /**
     * Processes the generated token and returns a TokenDTO object containing
     * the token.
     *
     * @param user The authenticated user.
     * @param token The generated token.
     * @return A TokenDTO object containing the token.
     * @throws DatabaseException If an error occurs while processing the token.
     */
    private TokenDTO processToken(User user, String token) {
        try {
            // Saves a new token for the specified user.
            this.saveToken(user, token);
            // Creates a new TokenDTO object with the generated token.
            TokenDTO tokenDTO = new TokenDTO(token);
            // Returns the TokenDTO object containing the token.
            return tokenDTO;
        } catch (Exception e) {
            // Logs the error message.
            LOGGER.error(e.getMessage());
            // Throws a DatabaseException with the error message.
            throw new DatabaseException(e.getMessage());
        }
    }
}
