package com.ecjtaneo.jwt_auth_demo.controller;

import com.ecjtaneo.jwt_auth_demo.dto.request.AuthRequestDto;
import com.ecjtaneo.jwt_auth_demo.dto.response.MessageResponse;
import com.ecjtaneo.jwt_auth_demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse register(@RequestBody @Valid AuthRequestDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse login (@RequestBody @Valid AuthRequestDto dto) {
        return authService.login(dto);
    }


}