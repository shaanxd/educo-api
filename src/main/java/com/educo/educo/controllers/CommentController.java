package com.educo.educo.controllers;

import com.educo.educo.DTO.Request.CommentRequest;
import com.educo.educo.entities.Comment;
import com.educo.educo.services.CommentService;
import com.educo.educo.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private CommentService commentService;
    private ValidationService validationService;

    @Autowired
    public CommentController(CommentService commentService, ValidationService validationService) {
        this.commentService = commentService;
        this.validationService = validationService;
    }

    @PostMapping("/create-comment")
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentRequest comment, BindingResult result) {
        if (result.hasErrors()) {
            return validationService.validate(result);
        }
        Comment newComment;
        Comment transformedComment = comment.transformToEntity();

        if (comment.getParentId() != null) {
            newComment = commentService.createChildComment(comment.getQuestionId(), comment.getParentId(), transformedComment);
        } else {
            newComment = commentService.createComment(comment.getQuestionId(), transformedComment);
        }
        return new ResponseEntity<>(newComment, HttpStatus.OK);
    }

    @PostMapping("/upvote/{id}")
    public ResponseEntity<?> upvoteComment(@PathVariable String id) {
        return new ResponseEntity<>(commentService.voteComment(id, true), HttpStatus.OK);
    }

    @PostMapping("/downvote/{id}")
    public ResponseEntity<?> downvoteComment(@PathVariable String id) {
        return new ResponseEntity<>(commentService.voteComment(id, false), HttpStatus.OK);
    }
}
