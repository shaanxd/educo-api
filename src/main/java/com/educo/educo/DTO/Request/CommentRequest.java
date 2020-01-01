package com.educo.educo.DTO.Request;

import com.educo.educo.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequest {
    @NotBlank(message = "Comment should belong to a question")
    private String questionId;
    @NotBlank(message = "Comment text is required")
    private String comment;

    private String parentId;

    public Comment transformToEntity() {
        return new Comment(this.comment);
    }
}
