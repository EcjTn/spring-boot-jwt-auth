package com.ecjtaneo.jwt_auth_demo.security;

import com.ecjtaneo.jwt_auth_demo.exception.RefreshTokenException;
import com.ecjtaneo.jwt_auth_demo.model.RefreshToken;
import com.ecjtaneo.jwt_auth_demo.model.User;
import com.ecjtaneo.jwt_auth_demo.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepo;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
    }

    public void save(RefreshToken refreshToken) {
        refreshTokenRepo.save(refreshToken);
    }

    //Recommended to hash the refresh token before storing it in db
    //in case the db gets hacked, hackers cant use it to generate access tokens.
    public RefreshToken generate(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshToken;
    }

    public RefreshToken validate(String refreshToken) {
        RefreshToken tokenRecord = refreshTokenRepo.findTokenByToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenException("Refresh token not found"));

        LocalDateTime currentDateTime = LocalDateTime.now();
        if(tokenRecord.getExpires_at().isBefore(currentDateTime)) {
            throw new RefreshTokenException("Refresh token is expired.");
        }

        return tokenRecord;
    }

    @Transactional
    public void deleteByUserId(Long id) {
        refreshTokenRepo.deleteAllByUserId(id);
    }

}
