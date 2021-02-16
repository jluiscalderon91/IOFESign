package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StationcounterNotFoundException extends RuntimeException {
    public StationcounterNotFoundException(String message) {
        super(message);
    }
}
