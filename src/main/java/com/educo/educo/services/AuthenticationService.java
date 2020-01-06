package com.educo.educo.services;

import com.educo.educo.entities.User;
import com.educo.educo.exceptions.GenericException;
import com.educo.educo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User registerUser(User user) {
        User foundEmailUser = userRepository.findUserByEmail(user.getEmail());
        // Check whether email is unique
        if (foundEmailUser != null) {
            throw new GenericException("Email already exists.", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
