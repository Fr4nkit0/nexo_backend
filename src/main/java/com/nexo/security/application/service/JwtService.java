package com.nexo.security.application.service;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
    String generateToken(UserDetails userDetails, Map<String, Object> extraClaims);

    String extractUsername(String jwt);

    String extractJwtFromRequest(HttpServletRequest request);

    SecretKey generateKey();

    Date extractExpiration(String jwt);

}
