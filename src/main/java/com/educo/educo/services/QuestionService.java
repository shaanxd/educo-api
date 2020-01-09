package com.educo.educo.services;

import com.educo.educo.DTO.Response.QuestionListResponse;
import com.educo.educo.DTO.Response.QuestionResponse;
import com.educo.educo.entities.Category;
import com.educo.educo.entities.Comment;
import com.educo.educo.entities.Question;
import com.educo.educo.entities.User;
import com.educo.educo.exceptions.GenericException;
import com.educo.educo.repositories.CategoryRepository;
import com.educo.educo.repositories.CommentRepository;
import com.educo.educo.repositories.QuestionRepository;
import com.educo.educo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private CommentRepository commentRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository, CategoryRepository categoryRepository, CommentRepository commentRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
    }

    public QuestionResponse createQuestion(Question question, String categoryId, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new GenericException("User not found", HttpStatus.NOT_FOUND);
        }
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new GenericException("Category not found", HttpStatus.NOT_FOUND);
        }
        question.setOwner(user);
        question.setCategory(category);
        return QuestionResponse.transformFromEntity(questionRepository.save(question));
    }

    public QuestionResponse getQuestion(String questionId, User user) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            throw new GenericException("Question not found", HttpStatus.NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Comment> comments = commentRepository.findByQuestion(question, pageable);
        QuestionResponse response = QuestionResponse.transformFromEntity(question);
        response.setCommentDetails(comments.getTotalPages(), comments.getNumber(), comments.getContent(), user);

        return response;
    }

    public QuestionListResponse getQuestions(Pageable pageable) {
        Page<Question> questionPage = questionRepository.findAll(pageable);
        return new QuestionListResponse(questionPage.getTotalPages(), questionPage.getNumber(), questionPage.getContent());
    }

    public QuestionListResponse getQuestionsByCategory(String id, Pageable pageable) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new GenericException("Category not found", HttpStatus.NOT_FOUND);
        }
        Page<Question> questionPage = questionRepository.findByCategory(category, pageable);
        return new QuestionListResponse(questionPage.getTotalPages(), questionPage.getNumber(), questionPage.getContent());
    }
}
