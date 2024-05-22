package com.chatop.api.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

public class TokenValidationException extends BaseApiException {

    public TokenValidationException(String message) {
        super(HttpStatus.UNAUTHORIZED, ApiCodeEnum.TOKEN_VALIDATION_FAILED, List.of(message));
    }
}
