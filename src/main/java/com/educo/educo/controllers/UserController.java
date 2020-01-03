package com.educo.educo.controllers;

import com.educo.educo.DTO.Request.LoginRequest;
import com.educo.educo.DTO.Response.AuthenticationResponse;
import com.educo.educo.entities.User;
import com.educo.educo.security.JwtTokenProvider;
import com.educo.educo.services.UserService;
import com.educo.educo.services.ValidationService;
import com.educo.educo.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.educo.educo.security.SecurityConstants.VALID_DURATION;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private ValidationService validationService;
    private UserValidator userValidator;
    private JwtTokenProvider tokenProvider;
    private AuthenticationManager manager;

    @Autowired
    public UserController(UserService userService, ValidationService validationService, UserValidator userValidator, JwtTokenProvider tokenProvider, AuthenticationManager manager) {
        this.userService = userService;
        this.validationService = validationService;
        this.userValidator = userValidator;
        this.tokenProvider = tokenProvider;
        this.manager = manager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(user);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, user.getId(), user.getFullName(), VALID_DURATION / 1000));
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
