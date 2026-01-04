package com.ecjtaneo.jwt_auth_demo.service.payload;

public record AuthTokens(
        String accessToken,
        String refreshToken
) {}
