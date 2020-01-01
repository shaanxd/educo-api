package com.educo.educo.services;

import com.educo.educo.entities.Comment;
import com.educo.educo.entities.Question;
import com.educo.educo.entities.Vote;
import com.educo.educo.exceptions.CommentException;
import com.educo.educo.exceptions.QuestionException;
import com.educo.educo.repositories.CommentRepository;
import com.educo.educo.repositories.QuestionRepository;
import com.educo.educo.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private QuestionRepository questionRepository;
    private VoteRepository voteRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, QuestionRepository questionRepository, VoteRepository voteRepository) {
        this.commentRepository = commentRepository;
        this.questionRepository = questionRepository;
        this.voteRepository = voteRepository;
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

    public Vote voteComment(String commentId, Boolean value) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new CommentException("Comment not found.");
        }
        Vote vote = voteRepository.findByComment_Id(commentId);
        if (vote == null) {
            vote = new Vote();
            vote.setComment(comment);
        }
        vote.setVote(value);
        return voteRepository.save(vote);
    }
}
