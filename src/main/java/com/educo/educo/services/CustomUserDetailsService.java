package com.educo.educo.services;

import com.educo.educo.entities.User;
import com.educo.educo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if(user==null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return user;
    }

    @Transactional
    public User loadUserById(String id) {
        User user = userRepository.findById(id).orElse(null);
        if(user==null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return user;
    }
}
