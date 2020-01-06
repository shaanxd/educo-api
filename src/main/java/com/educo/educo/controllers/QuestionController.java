package com.educo.educo.controllers;

import com.educo.educo.DTO.Request.QuestionRequest;
import com.educo.educo.entities.Question;
import com.educo.educo.services.QuestionService;
import com.educo.educo.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.educo.educo.constants.RouteConstants.*;

@RestController
@RequestMapping(QUESTION_ROOT)
public class QuestionController {
    private QuestionService questionService;
    private ValidationService validationService;

    @Autowired
    public QuestionController(QuestionService questionService, ValidationService validationService) {
        this.questionService = questionService;
        this.validationService = validationService;
    }

    @PostMapping(QUESTION_ADD_QUESTION)
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionRequest questionRequest, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        Question createdQuestion = questionService.createQuestion(questionRequest.transformToEntity(), authentication.getName());
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @GetMapping(QUESTION_GET_QUESTION)
    public ResponseEntity<?> getQuestionById(@PathVariable String id) {
        Question question = questionService.getQuestion(id);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @GetMapping(QUESTION_GET_QUESTIONS)
    public ResponseEntity<?> getQuestions() {
        return new ResponseEntity<>(questionService.getQuestions(), HttpStatus.OK);
    }
}
