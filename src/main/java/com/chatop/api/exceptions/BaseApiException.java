package com.chatop.api.exceptions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BaseApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ApiCodeEnum apiCodeEnum;
    private final List<String> errors;
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseApiException.class);

    public BaseApiException(HttpStatus httpStatus, ApiCodeEnum apiCodeEnum, List<String> messages) {
        super(String.join(", ", messages));
        this.httpStatus = httpStatus;
        this.apiCodeEnum = apiCodeEnum;
        this.errors = messages;
        LOGGER.error(this.toString());
    }
}
