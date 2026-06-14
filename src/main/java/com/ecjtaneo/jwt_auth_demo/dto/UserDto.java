package com.ecjtaneo.jwt_auth_demo.dto;

import java.time.LocalDateTime;

public record UserDto(
        String username,
        LocalDateTime created_at
) {}
