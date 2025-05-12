package com.nexo.user.infraestructura.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.nexo.user.application.dto.response.UserResponse;

public interface UserController {
    ResponseEntity<List<UserResponse>> findAllUsers(Authentication authentication);
}
