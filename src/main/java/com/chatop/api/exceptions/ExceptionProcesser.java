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

    @ExceptionHandler(BaseApiException.class)
    public ResponseEntity<ResponseDTO> handleBaseException(BaseApiException ex, WebRequest request) {
        logger.error("Error occurred: {}", ex.getMessage(), ex);

        ResponseDTO response = ResponseDTO.builder()
                .status(ex.getHttpStatus())
                .message(ex.getMessage())
                .errors(ex.getErrors())
                .build();
        // Returns a response entity with the constructed response object and HTTP status from the exception.
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

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

