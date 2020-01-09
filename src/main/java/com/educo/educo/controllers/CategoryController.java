package com.educo.educo.controllers;

import com.educo.educo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.educo.educo.constants.RouteConstants.CATEGORY_GET;
import static com.educo.educo.constants.RouteConstants.CATEGORY_ROOT;

@RestController
@RequestMapping(CATEGORY_ROOT)
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(CATEGORY_GET)
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }
}
