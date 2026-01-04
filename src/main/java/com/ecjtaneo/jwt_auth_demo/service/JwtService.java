package com.ecjtaneo.jwt_auth_demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.jwt.secret}")
    private String jwtKey;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(String sub) {
        Date expiry = new Date(System.currentTimeMillis() + 15 * 60 * 1000); //15 mins

        return Jwts
                .builder()
                .signWith(secretKey)
                .subject(sub)
                .expiration(expiry)
                .compact();
    }

    public Claims validate(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractSubject(Claims claims) {
        return claims.getSubject();
    }

}
