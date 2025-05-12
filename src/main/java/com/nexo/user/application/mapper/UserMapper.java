package com.nexo.user.application.mapper;

import org.springframework.stereotype.Service;

import com.nexo.auth.application.dto.response.RegisteredUser;
import com.nexo.user.application.dto.request.SaveUser;
import com.nexo.user.application.dto.response.UserResponse;
import com.nexo.user.domain.persistence.User;
import com.nexo.user.domain.util.Role;

@Service
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .lastSeen(user.getLastSeen())
                .isOnline(user.isUserOnline())
                .build();
    }

    public User toUser(SaveUser saveUser, String password, Role role) {
        if (saveUser == null) {
            return null;
        }
        return User.builder()
                .username(saveUser.getUsername())
                .password(password)
                .firstName(saveUser.getFirstName())
                .lastName(saveUser.getLastName())
                .email(saveUser.getEmail())
                .role(role)
                .build();

    }

    public RegisteredUser toGetRegisteredUserDto(User user, String jwt) {
        if (user == null)
            return null;
        return RegisteredUser.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .jwt(jwt)
                .build();
    }
}
