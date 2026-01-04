package com.ecjtaneo.jwt_auth_demo.service;

import com.ecjtaneo.jwt_auth_demo.dto.request.AuthRequestDto;
import com.ecjtaneo.jwt_auth_demo.dto.response.MessageResponse;
import com.ecjtaneo.jwt_auth_demo.mapper.UserMapper;
import com.ecjtaneo.jwt_auth_demo.model.RefreshToken;
import com.ecjtaneo.jwt_auth_demo.model.User;
import com.ecjtaneo.jwt_auth_demo.repository.RefreshTokenRepository;
import com.ecjtaneo.jwt_auth_demo.security.RefreshTokenService;
import com.ecjtaneo.jwt_auth_demo.security.UserDetailsImpl;
import com.ecjtaneo.jwt_auth_demo.service.dto.LoginResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public LoginResult login(AuthRequestDto dto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        Authentication authentication = authenticationManager.authenticate(token);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        String sub = user.getId().toString();

        String accessToken = jwtService.generate(sub);
        String refreshToken = refreshTokenService.generate(user).getToken();

        return new LoginResult(accessToken, refreshToken);
    }
}
