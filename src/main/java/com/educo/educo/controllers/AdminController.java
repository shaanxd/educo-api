package com.educo.educo.controllers;

import com.educo.educo.entities.Category;
import com.educo.educo.services.CategoryService;
import com.educo.educo.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.educo.educo.constants.RouteConstants.ADMIN_ADD_CATEGORY;
import static com.educo.educo.constants.RouteConstants.ADMIN_ROOT;

@RestController
@RequestMapping(ADMIN_ROOT)
public class AdminController {
    private ValidationService validationService;
    private CategoryService categoryService;

    @Autowired
    public AdminController(ValidationService validationService, CategoryService categoryService) {
        this.validationService = validationService;
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ADMIN_ADD_CATEGORY)
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category, BindingResult result) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        return ResponseEntity.ok(categoryService.createCategory(category));
    }
}
