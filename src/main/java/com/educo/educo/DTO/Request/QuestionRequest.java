package com.educo.educo.DTO.Request;

import com.educo.educo.entities.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    @NotNull(message = "Question title cannot be empty")
    private String title;

    @NotNull(message = "Question description cannot be empty")
    private String description;

    public Question transformToEntity() {
        return new Question(this.title, this.description);
    }
}
