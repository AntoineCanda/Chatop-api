package com.chatop.api.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

public class InvalidPathException extends BaseApiException {

    public InvalidPathException(String message) {
        super(HttpStatus.BAD_REQUEST, ApiCodeEnum.IMAGE_PATH_ERROR, List.of(message));
    }
}
