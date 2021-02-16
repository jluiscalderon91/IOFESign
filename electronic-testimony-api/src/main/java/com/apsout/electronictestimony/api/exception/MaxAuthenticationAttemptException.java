package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Error de credenciales")
public class MaxAuthenticationAttemptException extends AuthenticationException {
    public MaxAuthenticationAttemptException(String message) {
        super(message);
    }
}
