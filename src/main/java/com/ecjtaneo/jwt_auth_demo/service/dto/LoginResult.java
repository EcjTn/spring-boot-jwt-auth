package com.ecjtaneo.jwt_auth_demo.service.dto;

public record LoginResult(
        String accessToken,
        String refreshToken
) {}
