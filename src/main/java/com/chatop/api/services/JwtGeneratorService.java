package com.chatop.api.services;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chatop.api.exceptions.TokenException;
import com.chatop.api.exceptions.TokenValidationException;
import com.chatop.api.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

/**
 * Class that handles token
 */
@Service
@Data
public class JwtGeneratorService {

    private final String SECRET_KEY_STRING = this.encodeStringToBase64("9Yb$Pc5Jd8Af#Bg2Ek3Hm6Np9Rs4Tu7Wx0Zq3Rv6Yb9Ec2Vf5Ih8Mj1Lk4Nm7Qo#");
    private final SecretKey SECRET_KEY = this.getSigningKey();
    private static final long EXPIRATION_TIME = 3600*1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtGeneratorService.class);

    /**
     * Generates and returns a string token
     * @param user the User object whose email will be used as the token's subject
     * @return String (token) a JWT (JSON Web Token) string representing the token
     */
    public String generateToken(User user){
        try {
            LOGGER.info("Generating token...");
            
            return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
        } catch (InvalidKeyException e) {
            LOGGER.error(e.getMessage());
            throw new TokenException(e.getMessage());
        }
    }

    /**
     * Get token properties
     * @param token the token to extract claims from
     * @return Claims the claims extracted from the token
     */
    public Claims getClaims(String token){
        return extractAllClaims(token);
    }

    /**
     * Checks if the provided token is valid.
     *
     * @param token the token to validate
     * @param userDetails the UserDetails object representing the user whose token is being validated
     * @return true if the token is valid, false otherwise
     */
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractSubject(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Extracts the subject (email) from the provided token.
     *
     * @param token the token to extract the subject from
     * @return the subject (email) extracted from the token
     */
    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Checks if the provided token is expired.
     *
     * @param token the token to check for expiration
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    
    /**
     * Extracts the expiration date from the provided token.
     *
     * @param token the token to extract the expiration date from
     * @return the expiration date extracted from the token
     */
    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }


    /**
     * Extracts all claims from the provided token.
     *
     * @param token The token to extract claims from.
     * @return The claims extracted from the token.
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
            .verifyWith(SECRET_KEY)
            .build()
            .parseSignedClaims(token)
            .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            if(e instanceof JwtException) {
                throw new TokenValidationException(e.getMessage());
            }
            else {
                throw new TokenException(e.getMessage());
            }
        }
    }
    
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY_STRING);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String encodeStringToBase64(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }

}