package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class MaxUpdatePasswordAttemptException extends RuntimeException {
    public MaxUpdatePasswordAttemptException(String message) {
        super(message);
    }
}
