package com.ecjtaneo.jwt_auth_demo.service;

import com.ecjtaneo.jwt_auth_demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

}
