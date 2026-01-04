package com.ecjtaneo.jwt_auth_demo.repository;

import com.ecjtaneo.jwt_auth_demo.model.RefreshToken;
import com.ecjtaneo.jwt_auth_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    public Optional<RefreshToken> findTokenByToken(String token);
    public void deleteByToken(String token);
    public void deleteAllByUserId(Long userId);
    public void deleteAllByUser(User user);
}
