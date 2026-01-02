package com.ecjtaneo.jwt_auth_demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDto(
        @NotBlank
        @Size(min = 5, max = 30)
        String username,

        @NotBlank
        @Size(min = 8, max = 200)
        String password
) {}
