package com.nexo.auth.application.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.security.core.Authentication;

import com.nexo.auth.application.dto.request.AuthenticationRequest;
import com.nexo.auth.application.dto.response.AuthenticationResponse;
import com.nexo.auth.application.dto.response.RegisteredUser;
import com.nexo.auth.application.service.AuthenticationService;
import com.nexo.security.application.service.JwtService;
import com.nexo.security.domain.persistence.JwtToken;
import com.nexo.security.domain.repository.JwtRepository;
import com.nexo.user.application.dto.request.SaveUser;
import com.nexo.user.application.mapper.UserMapper;
import com.nexo.user.application.service.UserService;
import com.nexo.user.domain.persistence.User;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtRepository jwtRepository;

    public AuthenticationServiceImpl(UserService userService, UserMapper userMapper, JwtService jwtService,
            AuthenticationManager authenticationManager, JwtRepository jwtRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.jwtRepository = jwtRepository;
    }

    @Override
    public RegisteredUser registerOne(SaveUser saveUser) {
        User userRegistered = userService.registerUser(saveUser);
        String jwt = jwtService.generateToken(userRegistered, generateExtraClaims(userRegistered));
        saveUserToken(userRegistered, jwt);
        return userMapper.toGetRegisteredUserDto(userRegistered, jwt);
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword());
        this.authenticationManager.authenticate(authentication);
        User user = userService.findByUsername(authenticationRequest.getUsername());
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        saveUserToken(user, jwt);
        return AuthenticationResponse.builder()
                .jwt(jwt)
                .build();
    }

    @Override
    public void logout(HttpServletRequest request) {
        String jwt = jwtService.extractJwtFromRequest(request);
        if (!StringUtils.hasText(jwt))
            return;
        Optional<JwtToken> token = jwtRepository.findByToken(jwt);
        if (token.isPresent()) {
            JwtToken jwtToken = token.get();
            if (jwtToken.isValid()) {
                jwtToken.setValid(false);
                jwtRepository.save(jwtToken);
            }
        }
    }

    @Transactional(readOnly = true)
    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getFirstName());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("authorities", user.getAuthorities());
        return extraClaims;
    }

    private void saveUserToken(User user, String jwt) {
        JwtToken token = new JwtToken();
        token.setToken(jwt);
        token.setUser(user);
        token.setExpiration(jwtService.extractExpiration(jwt));
        token.setValid(true);
        jwtRepository.save(token);
    }

}
