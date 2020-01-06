package com.educo.educo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.educo.educo.constants.RouteConstants.ADMIN_GET_USERS;
import static com.educo.educo.constants.RouteConstants.ADMIN_ROOT;

@RestController
@RequestMapping(ADMIN_ROOT)
public class AdminController {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ADMIN_GET_USERS)
    public ResponseEntity<?> getUserList() {
        return new ResponseEntity<>("Auth successful!", HttpStatus.OK);
    }
}
