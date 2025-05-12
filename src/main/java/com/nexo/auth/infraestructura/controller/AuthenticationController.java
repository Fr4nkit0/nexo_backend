package com.nexo.auth.infraestructura.controller;

import org.springframework.http.ResponseEntity;

import com.nexo.auth.application.dto.request.AuthenticationRequest;
import com.nexo.auth.application.dto.response.AuthenticationResponse;
import com.nexo.auth.application.dto.response.LogoutResponse;
import com.nexo.auth.application.dto.response.RegisteredUser;
import com.nexo.user.application.dto.request.SaveUser;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationController {
    ResponseEntity<AuthenticationResponse> login(AuthenticationRequest authenticationRequest);

    ResponseEntity<LogoutResponse> logout(HttpServletRequest request);

    ResponseEntity<RegisteredUser> registerUser(SaveUser saveUser,
            HttpServletRequest request);
}
