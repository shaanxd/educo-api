package com.educo.educo.repositories;

import com.educo.educo.entities.Comment;
import com.educo.educo.entities.User;
import com.educo.educo.entities.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends CrudRepository<Vote, String> {
    Vote findByCommentAndOwner(Comment comment, User owner);
}
