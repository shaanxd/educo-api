package com.educo.educo.controllers;

import com.educo.educo.entities.User;
import com.educo.educo.services.UserService;
import com.educo.educo.services.ValidationService;
import com.educo.educo.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private ValidationService validationService;
    private UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, ValidationService validationService, UserValidator userValidator) {
        this.userService = userService;
        this.validationService = validationService;
        this.userValidator = userValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        // Validate passwords match
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            return validationService.validate(result);
        }

        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }

}
