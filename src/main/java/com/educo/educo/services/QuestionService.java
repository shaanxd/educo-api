package com.educo.educo.services;

import com.educo.educo.DTO.Response.QuestionResponse;
import com.educo.educo.entities.Category;
import com.educo.educo.entities.Question;
import com.educo.educo.entities.User;
import com.educo.educo.exceptions.GenericException;
import com.educo.educo.repositories.CategoryRepository;
import com.educo.educo.repositories.QuestionRepository;
import com.educo.educo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
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
        return QuestionResponse.transformFromEntity(questionRepository.save(question), user);
    }

    public QuestionResponse getQuestion(String questionId, User user) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            throw new GenericException("Question not found", HttpStatus.NOT_FOUND);
        }
        return QuestionResponse.transformFromEntity(question, user);
    }

    public List<QuestionResponse> getQuestions(User user) {
        return QuestionResponse.transformFromEntities(questionRepository.findAll(), user);
    }
}
