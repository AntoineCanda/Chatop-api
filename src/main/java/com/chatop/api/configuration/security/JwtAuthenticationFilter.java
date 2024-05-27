package com.chatop.api.configuration.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chatop.api.services.JwtGeneratorService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtGeneratorService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be just invoked
     * once per request within a single request thread. See
     * {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>
     * Provides HttpServletRequest and HttpServletResponse arguments instead of
     * the default ServletRequest and ServletResponse ones.
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @param filterChain chain of filters
     * @throws jakarta.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String requestURI = request.getRequestURI();
            // Do not filter login and register requests as well as swagger-ui and api-docs requests
            if (!requestURI.contains("/api/auth/login") && !requestURI.contains("/api/auth/register")
                    && !requestURI.contains("swagger-ui") && !requestURI.contains("v3/api-docs")) {
                verifyJwtToken(request, authHeader);
            }
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        }
    }

    /**
     * Verifies the JWT token in the request header and sets the authentication
     * context if the token is valid.
     *
     * @param request the HTTP request containing the authorization header
     * @param authHeader the authorization header containing the token
     * @throws AccessDeniedException if the token is invalid or missing
     */
    private void verifyJwtToken(HttpServletRequest request, String authHeader) {
        try {

            // Extract the token from the authorization header
            final String token;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new AccessDeniedException("Access denied");
            }

            token = authHeader.substring(7);
            userEmail = jwtService.extractSubject(token);

            // Check if the user is authenticated and the token is valid
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load the user details by the email from the token
                UserDetails currentUserDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Check if the token is valid for the current user
                if (Boolean.TRUE.equals(jwtService.isTokenValid(token, currentUserDetails))) {

                    // Create a new authentication token with the user details and authorities
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            currentUserDetails, null, currentUserDetails.getAuthorities());

                    // Set the authentication details with the request details
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication context with the new token
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (AccessDeniedException e) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
