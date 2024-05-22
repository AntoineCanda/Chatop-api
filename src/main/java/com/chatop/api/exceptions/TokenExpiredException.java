package com.chatop.api.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends BaseApiException {

    public TokenExpiredException(String message) {
        super(HttpStatus.UNAUTHORIZED, ApiCodeEnum.TOKEN_EXPIRED, List.of(message));
    }
}
