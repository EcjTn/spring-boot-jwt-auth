package com.ecjtaneo.jwt_auth_demo.service;

import com.ecjtaneo.jwt_auth_demo.dto.request.AuthRequestDto;
import com.ecjtaneo.jwt_auth_demo.dto.response.MessageResponse;
import com.ecjtaneo.jwt_auth_demo.mapper.UserMapper;
import com.ecjtaneo.jwt_auth_demo.model.RefreshToken;
import com.ecjtaneo.jwt_auth_demo.model.User;
import com.ecjtaneo.jwt_auth_demo.repository.RefreshTokenRepository;
import com.ecjtaneo.jwt_auth_demo.security.UserDetailsImpl;
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
    private RefreshTokenRepository refreshTokenRepository;

    public AuthService(
            UserService userService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public MessageResponse register(AuthRequestDto dto) {
        User user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        userService.create(user);

        return new MessageResponse("Successfully registered.");
    }

    public MessageResponse login(AuthRequestDto dto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        Authentication authentication = authenticationManager.authenticate(token);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        String sub = userId.toString();

        String jwt = jwtService.generate(sub);

        return new MessageResponse(jwt);
    }
}
