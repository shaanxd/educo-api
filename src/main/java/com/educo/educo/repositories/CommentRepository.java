package com.educo.educo.repositories;

import com.educo.educo.entities.Comment;
import com.educo.educo.entities.Question;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, String> {
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE #{#entityName} comment SET comment.voteCount = comment.voteCount + :value WHERE comment.id = :id")
    void upVoteComment(@Param("id") String id, @Param("value") int value);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE #{#entityName} comment SET comment.voteCount = comment.voteCount - :value WHERE comment.id = :id")
    void downVoteComment(@Param("id") String id, @Param("value") int value);

    Iterable<Comment> findByQuestion(Question question);
}
