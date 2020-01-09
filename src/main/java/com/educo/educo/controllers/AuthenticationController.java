package com.educo.educo.controllers;

import com.educo.educo.DTO.Request.LoginRequest;
import com.educo.educo.DTO.Response.AuthenticationResponse;
import com.educo.educo.entities.User;
import com.educo.educo.security.JwtTokenProvider;
import com.educo.educo.services.AuthenticationService;
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

import static com.educo.educo.constants.RouteConstants.*;
import static com.educo.educo.constants.SecurityConstants.VALID_DURATION;

@RestController
@RequestMapping(AUTH_ROOT)
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private ValidationService validationService;
    private UserValidator userValidator;
    private JwtTokenProvider tokenProvider;
    private AuthenticationManager manager;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, ValidationService validationService, UserValidator userValidator, JwtTokenProvider tokenProvider, AuthenticationManager manager) {
        this.authenticationService = authenticationService;
        this.validationService = validationService;
        this.userValidator = userValidator;
        this.tokenProvider = tokenProvider;
        this.manager = manager;
    }

    @PostMapping(AUTH_LOGIN)
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

    @PostMapping(AUTH_REGISTER)
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        return new ResponseEntity<>(authenticationService.registerUser(user), HttpStatus.CREATED);
    }

}
