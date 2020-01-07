package com.educo.educo.controllers;

import com.educo.educo.DTO.Request.CommentRequest;
import com.educo.educo.services.CommentService;
import com.educo.educo.services.ValidationService;
import com.educo.educo.utils.CheckUserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.educo.educo.constants.RouteConstants.*;

@RestController
@RequestMapping(COMMENT_ROOT)
public class CommentController {
    private CommentService commentService;
    private ValidationService validationService;
    private CheckUserAuth checkUserAuth;

    @Autowired
    public CommentController(CommentService commentService, ValidationService validationService, CheckUserAuth checkUserAuth) {
        this.commentService = commentService;
        this.validationService = validationService;
        this.checkUserAuth = checkUserAuth;
    }

    @PostMapping(COMMENT_ADD_COMMENT)
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentRequest comment, BindingResult result, Authentication auth) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        return ResponseEntity.ok(commentService.createComment(comment.getQuestionId(), comment.getParentId(), comment.transformToEntity(), checkUserAuth.checkAuth(auth)));
    }

    @PostMapping(COMMENT_VOTE)
    public ResponseEntity<?> voteComment(@PathVariable String id, HttpServletRequest request, Authentication auth) {
        boolean value = validationService.validateVoteParams(request);
        return ResponseEntity.ok(commentService.voteComment(id, value, checkUserAuth.checkAuth(auth)));
    }
}
