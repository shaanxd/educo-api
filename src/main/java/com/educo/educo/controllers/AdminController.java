package com.educo.educo.controllers;

import com.educo.educo.entities.Category;
import com.educo.educo.entities.User;
import com.educo.educo.services.AuthenticationService;
import com.educo.educo.services.CategoryService;
import com.educo.educo.services.ValidationService;
import com.educo.educo.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.educo.educo.constants.RouteConstants.*;

@RestController
@RequestMapping(ADMIN_ROOT)
public class AdminController {
    private UserValidator userValidator;
    private ValidationService validationService;
    private CategoryService categoryService;
    private AuthenticationService authenticationService;

    @Autowired
    public AdminController(UserValidator userValidator, ValidationService validationService, CategoryService categoryService, AuthenticationService authenticationService) {
        this.userValidator = userValidator;
        this.validationService = validationService;
        this.categoryService = categoryService;
        this.authenticationService = authenticationService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ADMIN_ADD_CATEGORY)
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category, BindingResult result) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ADMIN_ADD_USER)
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        return ResponseEntity.ok(authenticationService.registerAdminUser(user));
    }

}
