package com.educo.educo.controllers;

import com.educo.educo.DTO.Request.QuestionRequest;
import com.educo.educo.entities.Question;
import com.educo.educo.services.QuestionService;
import com.educo.educo.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;
    private final ValidationService validationService;

    @Autowired
    public QuestionController(QuestionService questionService, ValidationService validationService) {
        this.questionService = questionService;
        this.validationService = validationService;
    }

    @PostMapping("")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionRequest questionRequest, BindingResult result) {
        if(result.hasErrors()) {
            return validationService.validate(result);
        }
        Question createdQuestion = questionService.createQuestion(questionRequest.transformToEntity());
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable String id) {
        Question question = questionService.getQuestion(id);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> getQuestions() {
        return new ResponseEntity<>(questionService.getQuestions(), HttpStatus.OK);
    }
}
