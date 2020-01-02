package com.educo.educo.services;

import com.educo.educo.entities.User;
import com.educo.educo.exceptions.UserException;
import com.educo.educo.exceptions.ExceptionTypes.UserExceptionTypes;
import com.educo.educo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User registerUser(User user) {
        User foundEmailUser = userRepository.findUserByEmail(user.getEmail());
        // Check whether email is unique
        if(foundEmailUser != null) {
            throw new UserException(UserExceptionTypes.EMAIL, "Email already exists.");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
