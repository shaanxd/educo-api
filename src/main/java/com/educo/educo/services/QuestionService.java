package com.educo.educo.services;

import com.educo.educo.entities.Question;
import com.educo.educo.entities.User;
import com.educo.educo.exceptions.QuestionException;
import com.educo.educo.repositories.QuestionRepository;
import com.educo.educo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public Question createQuestion(Question question, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        question.setOwner(user);
        return questionRepository.save(question);
    }

    public Question getQuestion(String questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            throw new QuestionException("Question not found");
        }
        return question;
    }

    public Iterable<Question> getQuestions() {
        return questionRepository.findAll();
    }
}
