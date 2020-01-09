package com.educo.educo.DTO.Response;

import com.educo.educo.entities.Comment;
import com.educo.educo.entities.User;
import com.educo.educo.entities.Vote;
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
public class CommentResponse {
    private String id;
    private String comment;
    private List<CommentResponse> children = new ArrayList<>();
    private OwnerResponse owner;
    private int votes;
    private String voteType;

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
        return new CommentResponse(
                comment.getId(), comment.getComment(), children,
                owner, comment.getVoteCount(), voteType
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
