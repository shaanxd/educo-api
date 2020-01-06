package com.educo.educo.DTO.Response;

import com.educo.educo.entities.Question;
import com.educo.educo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    private String id;
    private String title;
    private String description;
    private OwnerResponse owner;
    private List<CommentResponse> comments = new ArrayList<>();

    public static QuestionResponse transformFromEntity(Question question, User user) {
        List<CommentResponse> comments = CommentResponse.transformFromEntities(question.getComments(), user);

        OwnerResponse owner = OwnerResponse.transformFromEntity(question.getOwner());

        return new QuestionResponse(
                question.getId(), question.getTitle(),
                question.getDescription(), owner, comments
        );
    }

    public static List<QuestionResponse> transformFromEntities(Iterable<Question> questions, User user) {
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : questions) {
            questionResponses.add(transformFromEntity(question, user));
        }
        return questionResponses;
    }
}
