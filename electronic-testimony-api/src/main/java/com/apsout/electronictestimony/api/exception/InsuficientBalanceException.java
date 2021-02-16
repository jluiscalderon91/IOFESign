package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
public class InsuficientBalanceException extends RuntimeException {
    public InsuficientBalanceException(String message) {
        super(message);
    }
}
