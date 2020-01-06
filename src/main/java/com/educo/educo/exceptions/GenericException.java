package com.educo.educo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericException extends RuntimeException {
    private HttpStatus statusCode;

    public GenericException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
