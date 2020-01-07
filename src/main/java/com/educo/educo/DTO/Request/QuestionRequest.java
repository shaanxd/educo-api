package com.educo.educo.DTO.Request;

import com.educo.educo.entities.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    @NotBlank(message = "Question title cannot be empty")
    private String title;

    @NotBlank(message = "Question description cannot be empty")
    private String description;

    @NotBlank(message = "Question category cannot be empty.")
    private String categoryId;

    public Question transformToEntity() {
        return new Question(this.title, this.description);
    }
}
