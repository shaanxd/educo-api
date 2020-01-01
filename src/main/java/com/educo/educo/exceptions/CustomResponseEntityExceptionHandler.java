package com.educo.educo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public final ResponseEntity<Object> handleQuestionException(QuestionException ex, WebRequest request) {
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleCommentException(CommentException ex, WebRequest request) {
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
