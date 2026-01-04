package com.ecjtaneo.jwt_auth_demo.service.dto;

public record AuthTokens(
        String accessToken,
        String refreshToken
) {}
