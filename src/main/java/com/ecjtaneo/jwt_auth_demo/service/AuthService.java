package com.ecjtaneo.jwt_auth_demo.service;

import com.ecjtaneo.jwt_auth_demo.dto.request.AuthRequestDto;
import com.ecjtaneo.jwt_auth_demo.dto.response.MessageResponse;
import com.ecjtaneo.jwt_auth_demo.mapper.UserMapper;
import com.ecjtaneo.jwt_auth_demo.model.RefreshToken;
import com.ecjtaneo.jwt_auth_demo.model.User;
import com.ecjtaneo.jwt_auth_demo.security.RefreshTokenService;
import com.ecjtaneo.jwt_auth_demo.security.UserDetailsImpl;
import com.ecjtaneo.jwt_auth_demo.service.payload.AuthTokens;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private RefreshTokenService refreshTokenService;

    public AuthService(
            UserService userService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public MessageResponse register(AuthRequestDto dto) {
        User user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        userService.create(user);

        return new MessageResponse("Successfully registered.");
    }

    public AuthTokens login(AuthRequestDto dto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        Authentication authentication = authenticationManager.authenticate(token);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        String sub = user.getId().toString();

        String accessToken = jwtService.generate(sub);
        RefreshToken refreshTokenObj = refreshTokenService.generate(user);
        String refreshToken = refreshTokenObj.getToken();

        refreshTokenService.save(refreshTokenObj);

        return new AuthTokens(accessToken, refreshToken);
    }

    @Transactional
    public AuthTokens refresh(String oldRefreshToken) {
        RefreshToken tokenRecord = refreshTokenService.validate(oldRefreshToken);
        User user = tokenRecord.getUser();
        String sub = user.getId().toString();

        String accessToken = jwtService.generate(sub);

        String refreshToken = UUID.randomUUID().toString();
        tokenRecord.setToken(refreshToken);
        tokenRecord.setExpires_at(LocalDateTime.now().plusDays(30));

        return new AuthTokens(accessToken, refreshToken);
    }
}
