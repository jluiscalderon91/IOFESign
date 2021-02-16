package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssignerNotFoundException extends RuntimeException {
    public AssignerNotFoundException(String message) {
        super(message);
    }
}
