package com.ecjtaneo.jwt_auth_demo.controller;

import com.ecjtaneo.jwt_auth_demo.dto.request.AuthRequestDto;
import com.ecjtaneo.jwt_auth_demo.dto.response.MessageResponse;
import com.ecjtaneo.jwt_auth_demo.service.AuthService;
import com.ecjtaneo.jwt_auth_demo.service.dto.LoginResult;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    private final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse register(@RequestBody @Valid AuthRequestDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login (@RequestBody @Valid AuthRequestDto dto, HttpServletResponse response) {
        LoginResult loginResult = authService.login(dto);
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, loginResult.refreshToken())
                .httpOnly(true)
                .secure(false) //true in prod
                .sameSite("Lax")
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse(loginResult.accessToken()));
    }

    @GetMapping("/refresh")
    public String refresh(@CookieValue(name = "refresh_token") String token) {
        return token;
    }


}