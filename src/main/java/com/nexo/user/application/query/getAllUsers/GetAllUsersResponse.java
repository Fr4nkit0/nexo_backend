package com.nexo.user.application.query.getAllUsers;

import com.nexo.user.domain.entity.User;

import java.util.List;

public record GetAllUsersResponse(List<User> users) {
}
