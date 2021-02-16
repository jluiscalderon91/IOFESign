package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class WorkflowNotFoundException extends RuntimeException {
    public WorkflowNotFoundException(String message) {
        super(message);
    }
}
