package com.nexo.user.infraestructura.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexo.user.application.dto.response.UserResponse;
import com.nexo.user.application.service.UserService;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserController {
    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers(Authentication authentication) {
        return ResponseEntity.ok(userService.findAllUsersExceptAuthenticated(authentication));
    }

}
