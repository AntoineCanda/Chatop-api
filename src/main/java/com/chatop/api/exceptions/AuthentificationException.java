package com.chatop.api.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

public class AuthentificationException extends BaseApiException {

    public AuthentificationException(String message) {
        super(HttpStatus.UNAUTHORIZED, ApiCodeEnum.AUTHENTICATION_FAILED, List.of(message));
    }

}
