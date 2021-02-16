package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class InexistentBalanceException extends RuntimeException {
    public InexistentBalanceException(String message) {
        super(message);
    }
}
