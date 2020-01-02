package com.educo.educo.exceptions;

import lombok.Getter;
import lombok.Setter;
import com.educo.educo.exceptions.ExceptionTypes.UserExceptionTypes;

@Getter
@Setter
public class UserException extends RuntimeException{
    private UserExceptionTypes type;

    public UserException(UserExceptionTypes type, String message) {
        super(message);
        this.type = type;
    }
}
