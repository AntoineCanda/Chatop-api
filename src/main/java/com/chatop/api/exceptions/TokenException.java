package com.chatop.api.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

public class TokenException extends BaseApiException {

    public TokenException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ApiCodeEnum.TOKEN_ERROR, List.of(message));
    }
}