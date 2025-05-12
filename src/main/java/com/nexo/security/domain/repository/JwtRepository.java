package com.nexo.security.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nexo.security.domain.persistence.JwtToken;

public interface JwtRepository extends JpaRepository<JwtToken, Long> {
    @Query("SELECT j FROM JwtToken  j WHERE j.token=?1")
    Optional<JwtToken> findByToken(String jwt);

}
