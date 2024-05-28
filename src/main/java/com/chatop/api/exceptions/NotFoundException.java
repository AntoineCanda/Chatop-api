package com.chatop.api.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseApiException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ApiCodeEnum.NOT_FOUND, List.of(message));
    }
}
