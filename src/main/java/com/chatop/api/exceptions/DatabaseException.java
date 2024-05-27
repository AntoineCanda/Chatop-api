package com.chatop.api.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

public class DatabaseException extends BaseApiException {

    public DatabaseException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ApiCodeEnum.DATABASE_ERROR, List.of(message));
    }
}
