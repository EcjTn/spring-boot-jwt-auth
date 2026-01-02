package com.ecjtaneo.jwt_auth_demo.service;

import com.ecjtaneo.jwt_auth_demo.dto.request.UserRegisterDto;
import com.ecjtaneo.jwt_auth_demo.dto.response.MessageResponse;
import com.ecjtaneo.jwt_auth_demo.model.User;
import com.ecjtaneo.jwt_auth_demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public MessageResponse add(UserRegisterDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());

        return new MessageResponse("Successfully registered.");
    }

}
