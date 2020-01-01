package com.educo.educo.repositories;

import com.educo.educo.entities.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends CrudRepository<Vote, String> {
    Vote findByComment_Id(String id);
}
