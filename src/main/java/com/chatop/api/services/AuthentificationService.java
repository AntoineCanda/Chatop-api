package com.chatop.api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.chatop.api.dto.TokenDTO;
import com.chatop.api.dto.UserDTO;
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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthentificationService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationService.class);
    private final AuthenticationManager authentificateurManager;
    private final PasswordEncoder passwordEncoder;
    
    private final IUserRepository userRepository;
    private final ITokenRepository tokenRepository;
    private final JwtGeneratorService jwtGeneratorService;

    public TokenDTO login(CredentialsDTO credentials) {
        LOGGER.info("Debut de l'authentification de l'utilisateur : {}", credentials.getEmail());

        authentificateurManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
        User user = userRepository.findByEmail(credentials.getEmail()).orElseThrow();

        LOGGER.info("Utilisateur trouve : {}", user.toString());
        this.disableAllTokensForUser(user);
        String token = jwtGeneratorService.generateToken(user);
        
        return this.processToken(user, token);
    }

    public TokenDTO register(RegisterUserDTO userData) {
        LOGGER.info("Debut de l'inscription de l'utilisateur : {}", userData.getEmail());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        User user = User.builder()
                .name(userData.getName())
                .email(userData.getEmail())
                .password(passwordEncoder.encode(userData.getPassword()))
                .role(Role.USER)
                .active(true)
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();
        User savedUser = userRepository.save(user);
        LOGGER.info("Utilisateur sauvegarde : {}", savedUser.getEmail());

        String token = jwtGeneratorService.generateToken(savedUser);
        return this.processToken(savedUser, token);
    }

    public UserDTO me(String token) {
        LOGGER.info("Recuperation de l'utilisateur a partir du token.");

        token = token.replace("Bearer ", "");
        TokenDTO tokenDTO = new TokenDTO(token);

        User user = this.getUserFromToken(tokenDTO);
        UserDTO userDTO = user.getUserDTOFromUser(user);

        return userDTO;
    }

    private User getUserFromToken(TokenDTO token){
        LOGGER.info("Obtention de l'utilisateur a partir du token.");

        String tokenValue = token.getToken();
        String email = jwtGeneratorService.extractSubject(tokenValue);
        User user = userRepository.findByEmail(email).orElseThrow();
        LOGGER.info("Utilisateur trouve : {}", user.getEmail());
        
        return user;
    }

    private void disableAllTokensForUser(User user) {
        LOGGER.info("Desactivation de tous les tokens valides pour l'utilisateur : {}", user.getEmail());

        List<Token> tokens = tokenRepository.findAllValidTokenByUser(user.getId());
        tokens.forEach(token -> {
            token.setValid(false);
            token.setExpired(true);
        });

        tokenRepository.saveAll(tokens);
    }

    private void saveToken(User user, String token) {
        LOGGER.info("Sauvegarde du nouveau token pour l'utilisateur : {}", user.getEmail());

        Token newToken = new Token();
        newToken.setToken(token);
        newToken.setValid(true);
        newToken.setExpired(false);
        newToken.setUser_id(user.getId());
        newToken.setType(TokenType.BEARER_TOKEN);
        newToken.setCreatedAt(new Timestamp( System.currentTimeMillis()));

        tokenRepository.save(newToken);
    }

    private TokenDTO processToken(User user, String token){

        this.saveToken(user, token);
        TokenDTO tokenDTO = new TokenDTO(token);
        
        return tokenDTO;
    }
}
