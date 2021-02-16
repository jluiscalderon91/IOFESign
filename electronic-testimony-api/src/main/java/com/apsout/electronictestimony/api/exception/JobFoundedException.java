package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class JobFoundedException extends RuntimeException{
    public JobFoundedException(String message) {
        super(message);
    }
}
