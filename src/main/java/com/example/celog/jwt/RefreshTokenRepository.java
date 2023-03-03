package com.example.celog.jwt;

import com.example.celog.jwt.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findAllByMemberId(String memberId);
    void deleteByMemberId(String memberId);
}