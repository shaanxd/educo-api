package com.educo.educo.controllers;

import com.educo.educo.DTO.Request.CommentRequest;
import com.educo.educo.entities.Comment;
import com.educo.educo.services.CommentService;
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
@RequestMapping(COMMENT_ROOT)
public class CommentController {
    private CommentService commentService;
    private ValidationService validationService;

    @Autowired
    public CommentController(CommentService commentService, ValidationService validationService) {
        this.commentService = commentService;
        this.validationService = validationService;
    }

    @PostMapping(COMMENT_ADD_COMMENT)
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentRequest comment, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        Comment newComment;
        Comment transformedComment = comment.transformToEntity();

        if (comment.getParentId() != null) {
            newComment = commentService.createChildComment(comment.getQuestionId(), comment.getParentId(), transformedComment, authentication.getName());
        } else {
            newComment = commentService.createComment(comment.getQuestionId(), transformedComment, authentication.getName());
        }
        return new ResponseEntity<>(newComment, HttpStatus.OK);
    }

    @PostMapping(COMMENT_UPVOTE)
    public ResponseEntity<?> upvoteComment(@PathVariable String id, Authentication authentication) {
        return new ResponseEntity<>(commentService.voteComment(id, true, authentication.getName()), HttpStatus.OK);
    }

    @PostMapping(COMMENT_DOWNVOTE)
    public ResponseEntity<?> downvoteComment(@PathVariable String id, Authentication authentication) {
        return new ResponseEntity<>(commentService.voteComment(id, false, authentication.getName()), HttpStatus.OK);
    }
}
