package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class InexistentServiceweightConfigurationException extends RuntimeException {
    public InexistentServiceweightConfigurationException(String message) {
        super(message);
    }
}
