package com.chatop.api.exceptions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.chatop.api.dto.ResponseDTO;

@ControllerAdvice
public class ExceptionProcesser {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionProcesser.class);

    /**
     * Handles {@link BaseApiException} by creating a {@link ResponseEntity}
     * with the appropriate status, message, and errors.
     *
     * @param ex the {@link BaseApiException} to handle
     * @param request the current web request
     * @return a {@link ResponseEntity} containing the response data
     */
    @ExceptionHandler(BaseApiException.class)
    public ResponseEntity<ResponseDTO> handleBaseException(BaseApiException ex, WebRequest request) {
        logger.error("Error occurred: {}", ex.getMessage(), ex);

        ResponseDTO response = ResponseDTO.builder()
                .status(ex.getHttpStatus())
                .message(ex.getMessage())
                .errors(ex.getErrors())
                .build();
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    /**
     * Handles unhandled exceptions by creating a {@link ResponseEntity} with
     * the appropriate status, message, and errors.
     *
     * @param ex the unhandled {@link Exception} to handle
     * @param request the current web request
     * @return a {@link ResponseEntity} containing the response data
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleException(Exception ex, WebRequest request) {
        logger.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
        ResponseDTO response = ResponseDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("An unexpected error occurred.")
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
