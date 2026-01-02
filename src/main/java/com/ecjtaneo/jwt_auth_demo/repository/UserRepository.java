package com.ecjtaneo.jwt_auth_demo.repository;

import com.ecjtaneo.jwt_auth_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByUsername(String username);
    public Optional<User> findByUsername();
}
