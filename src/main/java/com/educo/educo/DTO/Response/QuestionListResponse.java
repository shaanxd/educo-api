package com.educo.educo.DTO.Response;

import com.educo.educo.entities.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class QuestionListResponse {
    private long total;
    private long current;
    private List<QuestionResponse> questions = new ArrayList<>();

    public QuestionListResponse(long total, long current, List<Question> questions) {
        this.total = (total == 0) ? total : total - 1;
        this.current = current;
        for (Question question : questions) {
            this.questions.add(QuestionResponse.transformFromEntity(question));
        }
    }
}
