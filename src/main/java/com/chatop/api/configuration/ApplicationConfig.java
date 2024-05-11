package com.chatop.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.chatop.api.repositories.IUserRepository;
import com.chatop.api.services.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final IUserRepository userRepository;

/**
     * New PasswordEncoder instance
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userRepository); // Implémentez CustomUserDetailsService
    }

    /**
     * Bean method to create an instance of {@link UserDetailsService}.
     * This service is responsible for loading user-specific data from the database.
     *
     * @return an instance of {@link CustomUserDetailsService} that uses the provided {@link IUserRepository}
     */
     @Bean
    public AuthenticationProvider authenticationProvider(IUserRepository userRepository) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * Bean method to create an instance of {@link AuthenticationManager}.
     * This class is responsible for authenticating users by delegating to the
     * {@link AuthenticationProvider}.
     *
     * @param configuration The {@link AuthenticationConfiguration} instance.
     * @return An instance of {@link AuthenticationManager}.
     * @throws Exception If there is an error while creating the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
