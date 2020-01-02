package com.educo.educo.repositories;

import com.educo.educo.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findUserByEmail(String email);
}
