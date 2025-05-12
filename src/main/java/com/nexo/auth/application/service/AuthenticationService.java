package com.nexo.auth.application.service;

import com.nexo.auth.application.dto.request.AuthenticationRequest;
import com.nexo.auth.application.dto.response.AuthenticationResponse;
import com.nexo.auth.application.dto.response.RegisteredUser;
import com.nexo.user.application.dto.request.SaveUser;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    RegisteredUser registerOne(SaveUser saveUser);

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);

    void logout(HttpServletRequest request);

}
