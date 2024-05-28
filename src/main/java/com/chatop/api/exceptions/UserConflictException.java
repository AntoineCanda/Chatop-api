package com.chatop.api.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

public class UserConflictException extends BaseApiException {

    public UserConflictException(String message) {
        super(HttpStatus.CONFLICT, ApiCodeEnum.USER_CONFLICT, List.of(message));
    }

}
