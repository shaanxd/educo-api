package com.educo.educo.services;

import com.educo.educo.entities.Comment;
import com.educo.educo.entities.Question;
import com.educo.educo.exceptions.CommentException;
import com.educo.educo.exceptions.QuestionException;
import com.educo.educo.repositories.CommentRepository;
import com.educo.educo.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, QuestionRepository questionRepository) {
        this.commentRepository = commentRepository;
        this.questionRepository = questionRepository;
    }

    public Comment createComment(String questionID, Comment comment) {
        Question question = questionRepository.findById(questionID).orElse(null);
        if (question == null) {
            throw new QuestionException("Question not found.");
        }
        comment.setQuestion(question);
        return commentRepository.save(comment);
    }

    public Comment createChildComment(String questionId, String parentId, Comment comment) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            throw new QuestionException("Question not found.");
        }
        Comment parent = commentRepository.findById(parentId).orElse(null);
        if (parent == null) {
            throw new CommentException("Comment not found.");
        }
        comment.setQuestion(question);
        comment.setParent(parent);
        return commentRepository.save(comment);
    }

    public Comment upvoteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new CommentException("Comment not found.");
        }
        comment.setVoteCount(comment.getVoteCount() + 1);
        return commentRepository.save(comment);
    }

    public Comment downvoteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new CommentException("Comment not found.");
        }
        comment.setVoteCount(comment.getVoteCount() - 1);
        return commentRepository.save(comment);
    }
}
