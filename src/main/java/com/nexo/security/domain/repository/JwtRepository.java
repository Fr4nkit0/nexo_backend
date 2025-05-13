package com.nexo.security.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nexo.security.domain.persistence.JwtToken;

@Repository
public interface JwtRepository extends JpaRepository<JwtToken, Long> {
    @Query("SELECT j FROM JwtToken  j WHERE j.token=?1")
    Optional<JwtToken> findByToken(String jwt);

}
