package com.chatop.api.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

public class UploadException extends BaseApiException {

    public UploadException(String message) {
        super(HttpStatus.BAD_REQUEST, ApiCodeEnum.UPLOAD_ERROR, List.of(message));
    }
}
