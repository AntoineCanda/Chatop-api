package com.chatop.api.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;


public class CreationException extends BaseApiException {

    public CreationException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ApiCodeEnum.CREATION_ERROR, List.of(message));
    }
}
