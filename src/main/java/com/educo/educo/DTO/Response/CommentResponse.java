package com.educo.educo.DTO.Response;

import com.educo.educo.entities.Comment;
import com.educo.educo.entities.User;
import com.educo.educo.entities.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String id;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
    private long votes;
    private String voteType;
    private long childCount;
    private OwnerResponse owner;
    private List<CommentResponse> children = new ArrayList<>();

    public static CommentResponse transformToEntity(Comment comment, User user) {
        List<CommentResponse> children = new ArrayList<>();
        for (Comment nestedComment : comment.getChildren()) {
            children.add(transformToEntity(nestedComment, user));
        }
        OwnerResponse owner = OwnerResponse.transformFromEntity(comment.getOwner());
        String voteType = null;
        if (user != null) {
            for (Vote vote : comment.getVotes()) {
                if (vote.getOwner().getId().equals(user.getId())) {
                    if (vote.isVote()) {
                        voteType = "VOTE_UP";
                    } else {
                        voteType = "VOTE_DOWN";
                    }
                    break;
                }
            }
        }
        if (voteType == null) {
            voteType = "VOTE_EMPTY";
        }
        long voteCount = comment.getPositive() - comment.getNegative();
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                voteCount,
                voteType,
                comment.getChildCount(),
                owner,
                children
        );
    }

    public static List<CommentResponse> transformFromEntities(Iterable<Comment> comments, User user) {
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponseList.add(transformToEntity(comment, user));
        }

        return commentResponseList;
    }
}
