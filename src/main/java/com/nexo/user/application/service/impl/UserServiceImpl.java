package com.nexo.user.application.service.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexo.security.application.validation.PasswordValidator;
import com.nexo.user.application.dto.request.SaveUser;
import com.nexo.user.application.dto.response.UserResponse;
import com.nexo.user.application.mapper.UserMapper;
import com.nexo.user.application.service.UserService;
import com.nexo.user.domain.persistence.User;
import com.nexo.user.domain.repository.UserRepository;
import com.nexo.user.domain.util.Role;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return this.getUserByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAllUsersExceptAuthenticated(Authentication connectedUser) {
        // Cambiar en el futuro dado que esta funcion me trae todos los Usuarios
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public User registerUser(SaveUser saveUser) {
        PasswordValidator.validatePassword(saveUser.getPassword(), saveUser.getPasswordRepeated());
        return userRepository.save(
                userMapper.toUser(saveUser, passwordEncoder.encode(saveUser.getPassword()), Role.ROLE_USER));
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();

    }

}
