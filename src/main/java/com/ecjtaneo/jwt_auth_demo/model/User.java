package com.ecjtaneo.jwt_auth_demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    private LocalDateTime created_at = LocalDateTime.now();

    @OneToMany(mappedBy = "user")
    private List<RefreshToken> refreshTokens;
}
