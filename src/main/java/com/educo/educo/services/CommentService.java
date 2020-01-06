package com.educo.educo.services;

import com.educo.educo.DTO.Response.CommentResponse;
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
import org.springframework.transaction.annotation.Transactional;

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

    public Iterable<CommentResponse> createComment(String questionID, String parentID, Comment comment, User user) {
        if (user == null) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        }

        Question question = questionRepository.findById(questionID).orElse(null);
        if (question == null) {
            throw new GenericException("Question not found.", HttpStatus.NOT_FOUND);
        }

        if (parentID != null) {
            Comment parent = commentRepository.findById(parentID).orElse(null);
            if (parent == null) {
                throw new GenericException("Parent comment not found", HttpStatus.NOT_FOUND);
            }
            comment.setParent(parent);
        } else {
            comment.setQuestion(question);
        }

        comment.setOwner(user);
        commentRepository.save(comment);

        Iterable<Comment> commentList = commentRepository.findByQuestion(question);

        return CommentResponse.transformFromEntities(commentList, user);
    }

    @Transactional
    public CommentResponse voteComment(String commentId, Boolean value, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        }
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new GenericException("Comment not found.", HttpStatus.NOT_FOUND);
        }
        Vote vote = voteRepository.findByCommentAndOwner(comment, user);
        if (vote != null) {
            if (vote.isVote() == value) {
                return CommentResponse.transformToEntity(comment, user);
            }
        } else {
            vote = new Vote(comment, user);
        }
        if (value) {
            commentRepository.upVoteComment(comment.getId());
        } else {
            commentRepository.downVoteComment(comment.getId());
        }
        vote.setVote(value);
        voteRepository.save(vote);

        return CommentResponse.transformToEntity(commentRepository.findById(commentId).orElse(comment), user);
    }
}
