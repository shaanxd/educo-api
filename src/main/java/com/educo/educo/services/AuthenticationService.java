package com.educo.educo.services;

import com.educo.educo.DTO.Response.AuthenticationResponse;
import com.educo.educo.entities.User;
import com.educo.educo.exceptions.GenericException;
import com.educo.educo.repositories.UserRepository;
import com.educo.educo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.educo.educo.constants.AuthConstants.AUTH_ADMIN;
import static com.educo.educo.constants.AuthConstants.AUTH_USER;
import static com.educo.educo.constants.SecurityConstants.VALID_DURATION;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public AuthenticationService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public AuthenticationResponse registerUser(User user) {
        User savedUser = registerAnyUser(user, AUTH_USER);
        String token = tokenProvider.generateToken(savedUser);
        return new AuthenticationResponse(token, user.getId(), user.getFullName(), user.getRole(), VALID_DURATION / 1000);
    }

    public User registerAdminUser(User user) {
        return registerAnyUser(user, AUTH_ADMIN);
    }

    private User registerAnyUser(User user, String role) {
        User foundEmailUser = userRepository.findUserByEmail(user.getEmail());
        if (foundEmailUser != null) {
            throw new GenericException("Email already exists.", HttpStatus.BAD_REQUEST);
        }
        user.setRole(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
