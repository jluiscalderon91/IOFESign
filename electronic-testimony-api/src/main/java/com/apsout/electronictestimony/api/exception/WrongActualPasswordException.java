package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongActualPasswordException extends RuntimeException {
    public WrongActualPasswordException(String message) {
        super(message);
    }
}
