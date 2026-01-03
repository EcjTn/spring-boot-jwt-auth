package com.ecjtaneo.jwt_auth_demo.controller;

import com.ecjtaneo.jwt_auth_demo.dto.request.UserRegisterDto;
import com.ecjtaneo.jwt_auth_demo.dto.response.MessageResponse;
import com.ecjtaneo.jwt_auth_demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public MessageResponse register(@RequestBody @Valid UserRegisterDto dto) {
        return userService.add(dto);
    }


}