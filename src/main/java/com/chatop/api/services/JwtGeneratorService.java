package com.chatop.api.services;

import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chatop.api.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

/**
 * Class that handles token
 */
@Service
@Data
public class JwtGeneratorService {

    private static final String SECRET_KEY = "9Yb$Pc5Jd8Af#Bg2Ek3Hm6Np9Rs4Tu7Wx0Zq3Rv6Yb9Ec2Vf5Ih8Mj1Lk4Nm7Qo#";
    private static final long EXPIRATION_TIME = 3600;

    /**
     * Generates and returns a string token
     * @param user the User object whose email will be used as the token's subject
     * @return String (token) a JWT (JSON Web Token) string representing the token
     */
    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
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
        return extractClaimsData(token, Claims::getSubject);
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
     * Extracts a specific claim from the provided token.
     *
     * @param token the token to extract the claim from
     * @param claimsResolver a function that takes a {@link Claims} object and returns the desired claim
     * @return the extracted claim
     */
    private <T> T extractClaimsData(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts the expiration date from the provided token.
     *
     * @param token the token to extract the expiration date from
     * @return the expiration date extracted from the token
     */
    private Date extractExpiration(String token) {
        return extractClaimsData(token, Claims::getExpiration);
    }


    /**
     * Extracts all claims from the provided token.
     *
     * @param token The token to extract claims from.
     * @return The claims extracted from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY )
                .parseClaimsJws(token)
                .getBody();
    }
}