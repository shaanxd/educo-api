package com.educo.educo.repositories;

import com.educo.educo.entities.Comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, String> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE #{#entityName} comment SET comment.voteCount = comment.voteCount + 1 WHERE comment.id = :id")
    void upVoteComment(@Param("id") String id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE #{#entityName} comment SET comment.voteCount = comment.voteCount - 1 WHERE comment.id = :id")
    void downVoteComment(@Param("id") String id);
}
