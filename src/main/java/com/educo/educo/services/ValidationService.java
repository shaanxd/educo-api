package com.educo.educo.services;

import com.educo.educo.exceptions.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationService {

    public ResponseEntity<?> validate(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    public boolean validateVoteParams(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        String[] key = paramMap.get("type");
        if (key == null || key.length == 0) {
            throw new GenericException("Bad Request", HttpStatus.BAD_REQUEST);
        } else if (key[0].toLowerCase().equals("upvote")) {
            return true;
        } else if (key[0].toLowerCase().equals("downvote")) {
            return false;
        } else {
            throw new GenericException("Vote type invalid. Please try again.", HttpStatus.BAD_REQUEST);
        }
    }
}
