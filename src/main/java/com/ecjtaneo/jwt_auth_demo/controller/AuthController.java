package com.ecjtaneo.jwt_auth_demo.controller;

import com.ecjtaneo.jwt_auth_demo.dto.request.AuthRequestDto;
import com.ecjtaneo.jwt_auth_demo.dto.response.MessageResponse;
import com.ecjtaneo.jwt_auth_demo.service.AuthService;
import com.ecjtaneo.jwt_auth_demo.service.dto.AuthTokens;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//Cookies(refresh tokens)'s secure option should be TRUE in prod

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
        AuthTokens tokens = authService.login(dto);
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, tokens.refreshToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse(tokens.accessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<MessageResponse> refresh(@CookieValue(name = "refresh_token") String token) {
        AuthTokens tokens = authService.refresh(token);
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, tokens.refreshToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse(tokens.accessToken()));
    }


}