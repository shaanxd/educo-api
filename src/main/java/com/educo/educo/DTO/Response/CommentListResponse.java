package com.educo.educo.DTO.Response;

import com.educo.educo.entities.Comment;
import com.educo.educo.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentListResponse {
    private long total = 0;
    private long current = 0;
    private List<CommentResponse> comments = new ArrayList<>();

    public CommentListResponse(long total, long current, List<Comment> comments, User user) {
        this.total = (total == 0) ? total : total - 1;
        this.current = current;
        for (Comment comment : comments) {
            this.comments.add(CommentResponse.transformToEntity(comment, user));
        }
    }
}
