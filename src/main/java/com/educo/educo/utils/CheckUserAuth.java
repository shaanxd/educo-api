package com.educo.educo.utils;

import com.educo.educo.entities.User;
import com.educo.educo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CheckUserAuth {

    private UserRepository userRepository;

    @Autowired
    public CheckUserAuth(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User checkAuth(Authentication authentication) {
        User user = null;

        if (authentication != null && authentication.getName() != null && StringUtils.hasText(authentication.getName())) {
            user = userRepository.findById(authentication.getName()).orElse(null);
        }

        return user;
    }
}
