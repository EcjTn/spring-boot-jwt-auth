package com.ecjtaneo.jwt_auth_demo.service;

import com.ecjtaneo.jwt_auth_demo.exception.ResourceConflictException;
import com.ecjtaneo.jwt_auth_demo.model.User;
import com.ecjtaneo.jwt_auth_demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;

    @Transactional
    public User create(User user) {
        if(userRepo.existsByUsername(user.getUsername())) {
            throw new ResourceConflictException("Username already taken");
        }
        return userRepo.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

}
