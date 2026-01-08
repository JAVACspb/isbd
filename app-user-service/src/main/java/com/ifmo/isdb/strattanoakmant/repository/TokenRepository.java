package com.ifmo.isdb.strattanoakmant.repository;

import com.ifmo.isdb.strattanoakmant.model.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenRepository extends JpaRepository<JwtToken, String> {
    int deleteByLocalDateTimeBefore(LocalDateTime cutoff);
}
