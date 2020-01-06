package com.educo.educo.services;

import com.educo.educo.entities.Comment;
import com.educo.educo.entities.Question;
import com.educo.educo.entities.User;
import com.educo.educo.entities.Vote;
import com.educo.educo.exceptions.GenericException;
import com.educo.educo.repositories.CommentRepository;
import com.educo.educo.repositories.QuestionRepository;
import com.educo.educo.repositories.UserRepository;
import com.educo.educo.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private QuestionRepository questionRepository;
    private VoteRepository voteRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, QuestionRepository questionRepository, VoteRepository voteRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.questionRepository = questionRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    public Comment createComment(String questionID, Comment comment, String userId) {
        Question question = questionRepository.findById(questionID).orElse(null);

        if (question == null) {
            throw new GenericException("Question not found.", HttpStatus.NOT_FOUND);
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        }

        comment.setQuestion(question);
        comment.setOwner(user);
        return commentRepository.save(comment);
    }

    public Comment createChildComment(String questionId, String parentId, Comment comment, String userId) {
        Comment parent = commentRepository.findById(parentId).orElse(null);
        if (parent == null) {
            throw new GenericException("Comment not found.", HttpStatus.NOT_FOUND);
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        }

        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            throw new GenericException("Question not found.", HttpStatus.NOT_FOUND);
        }

        comment.setOwner(user);
        comment.setQuestion(question);
        comment.setParent(parent);
        return commentRepository.save(comment);
    }

    public Vote voteComment(String commentId, Boolean value, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        }

        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new GenericException("Comment not found.", HttpStatus.NOT_FOUND);
        }

        Vote vote = voteRepository.findByCommentAndOwner(comment, user);
        if (vote == null) {
            vote = new Vote();
            vote.setComment(comment);
            vote.setOwner(user);
        }
        vote.setVote(value);
        return voteRepository.save(vote);
    }
}
