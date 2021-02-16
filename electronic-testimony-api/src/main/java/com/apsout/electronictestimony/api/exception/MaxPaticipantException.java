package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class MaxPaticipantException extends RuntimeException {
    public MaxPaticipantException(String message) {
        super(message);
    }
}
