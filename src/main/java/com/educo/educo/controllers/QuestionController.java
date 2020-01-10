package com.educo.educo.controllers;

import com.educo.educo.DTO.Request.QuestionRequest;
import com.educo.educo.DTO.Response.QuestionListResponse;
import com.educo.educo.DTO.Response.QuestionResponse;
import com.educo.educo.services.QuestionService;
import com.educo.educo.services.ValidationService;
import com.educo.educo.utils.CheckUserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.educo.educo.constants.RouteConstants.*;

@RestController
@RequestMapping(QUESTION_ROOT)
public class QuestionController {
    private QuestionService questionService;
    private ValidationService validationService;
    private CheckUserAuth checkUserAuth;

    @Autowired
    public QuestionController(QuestionService questionService, ValidationService validationService, CheckUserAuth checkUserAuth) {
        this.questionService = questionService;
        this.validationService = validationService;
        this.checkUserAuth = checkUserAuth;
    }

    @PostMapping(QUESTION_ADD_QUESTION)
    public ResponseEntity<?> createQuestion(@Valid QuestionRequest questionRequest, @RequestPart("files") MultipartFile[] files, BindingResult result, Authentication auth) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        QuestionResponse question = questionService.createQuestion(questionRequest.transformToEntity(), questionRequest.getCategoryId(), files, auth.getName());
        return ResponseEntity.ok(question);
    }

    @GetMapping(QUESTION_GET_QUESTION)
    public ResponseEntity<?> getQuestion(@PathVariable String id, Authentication auth) {
        QuestionResponse question = questionService.getQuestion(id, checkUserAuth.checkAuth(auth));
        return ResponseEntity.ok(question);
    }

    @GetMapping(QUESTION_GET_BY_CATEGORY)
    public ResponseEntity<?> getQuestionsByCategory(@PathVariable String id, Pageable pageable) {
        QuestionListResponse questions = questionService.getQuestionsByCategory(id, pageable);
        return ResponseEntity.ok(questions);
    }

    @GetMapping(QUESTION_GET)
    public ResponseEntity<?> getQuestions(Pageable pageable) {
        QuestionListResponse questions = questionService.getQuestions(pageable);
        return ResponseEntity.ok(questions);
    }
}
