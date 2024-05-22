package com.chatop.api.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseApiException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, ApiCodeEnum.UNAUTHORIZED , List.of(message));
    }
}
