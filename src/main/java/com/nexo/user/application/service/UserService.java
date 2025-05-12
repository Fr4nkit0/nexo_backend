package com.nexo.user.application.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.nexo.user.application.dto.request.SaveUser;
import com.nexo.user.application.dto.response.UserResponse;
import com.nexo.user.domain.persistence.User;

public interface UserService {

    List<UserResponse> findAllUsersExceptAuthenticated(Authentication connectedUser);

    User registerUser(SaveUser saveUser);
}
