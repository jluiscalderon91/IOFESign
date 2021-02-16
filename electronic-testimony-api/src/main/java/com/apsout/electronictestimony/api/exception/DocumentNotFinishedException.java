package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class DocumentNotFinishedException extends RuntimeException {
    public DocumentNotFinishedException(String message) {
        super(message);
    }
}
