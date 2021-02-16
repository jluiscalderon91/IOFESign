package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ScopeNotFoundException extends RuntimeException {
    public ScopeNotFoundException(String message) {
        super(message);
    }
}
