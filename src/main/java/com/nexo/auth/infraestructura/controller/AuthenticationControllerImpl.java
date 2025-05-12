package com.nexo.auth.infraestructura.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexo.auth.application.dto.request.AuthenticationRequest;
import com.nexo.auth.application.dto.response.AuthenticationResponse;
import com.nexo.auth.application.dto.response.LogoutResponse;
import com.nexo.auth.application.dto.response.RegisteredUser;
import com.nexo.auth.application.service.AuthenticationService;
import com.nexo.user.application.dto.request.SaveUser;
import com.nexo.user.application.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticationControllerImpl implements AuthenticationController {
    private AuthenticationService authenticationService;
    private UserService userService;

    @Override
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.ok(new LogoutResponse("Logout exitoso"));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisteredUser> registerUser(SaveUser saveUser, HttpServletRequest request) {
        RegisteredUser registeredUser = authenticationService.registerOne(saveUser);
        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + registeredUser.getUsername());
        return ResponseEntity.created(newLocation).body(registeredUser);
    }

}
