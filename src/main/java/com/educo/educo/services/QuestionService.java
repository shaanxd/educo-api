package com.educo.educo.services;

import com.educo.educo.entities.Question;
import com.educo.educo.exceptions.QuestionException;
import com.educo.educo.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Question question) {
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
