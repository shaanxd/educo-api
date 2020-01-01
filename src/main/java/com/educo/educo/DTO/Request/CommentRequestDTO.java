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
public class CommentRequestDTO {
    @NotBlank(message = "Comment should belong to a question")
    private String questionId;
    @NotBlank(message = "Comment text is required")
    private String comment;

    private String parentId;

    public static Comment transformToEntity(CommentRequestDTO commentRequestDTO) {
        Comment comment = new Comment();
        comment.setComment(commentRequestDTO.getComment());
        return comment;
    }
}
