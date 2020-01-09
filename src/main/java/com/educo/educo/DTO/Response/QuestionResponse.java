package com.educo.educo.DTO.Response;

import com.educo.educo.entities.Comment;
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
    private CategoryResponse category;
    private long total = 0;
    private long current = 0;
    private List<CommentResponse> comments = new ArrayList<>();

    private QuestionResponse(String id, String title, String description, OwnerResponse owner, CategoryResponse category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.category = category;
    }

    public static QuestionResponse transformFromEntity(Question question) {
        OwnerResponse questionOwner = OwnerResponse.transformFromEntity(question.getOwner());
        CategoryResponse questionCategory = CategoryResponse.transformFromEntity(question.getCategory());

        return new QuestionResponse(
                question.getId(),
                question.getTitle(),
                question.getDescription(),
                questionOwner,
                questionCategory
        );
    }

    public static List<QuestionResponse> transformFromEntities(Iterable<Question> questions) {
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : questions) {
            questionResponses.add(transformFromEntity(question));
        }
        return questionResponses;
    }

    public void setCommentDetails(long total, long current, List<Comment> comments, User user) {
        this.total = (total == 0) ? total : total - 1;
        this.current = current;
        for (Comment comment : comments) {
            this.comments.add(CommentResponse.transformToEntity(comment, user));
        }
    }
}
