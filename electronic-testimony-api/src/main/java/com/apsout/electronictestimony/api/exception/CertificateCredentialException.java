package com.apsout.electronictestimony.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CertificateCredentialException extends RuntimeException {
    public CertificateCredentialException(String message) {
        super(message);
    }
}
