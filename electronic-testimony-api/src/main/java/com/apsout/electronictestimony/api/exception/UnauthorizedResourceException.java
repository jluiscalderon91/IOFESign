package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedResourceException extends RuntimeException {
    public UnauthorizedResourceException(String message) {
        super(message);
    }
}
