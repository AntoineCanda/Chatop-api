package com.chatop.api.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

/**
 *
 * @author BarbieSophie
 */
public class BadRequestException extends BaseApiException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, ApiCodeEnum.BAD_REQUEST, List.of(message));
    }
}
