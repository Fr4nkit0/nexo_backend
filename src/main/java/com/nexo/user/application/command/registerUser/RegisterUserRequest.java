package com.nexo.user.application.command.registerUser;

import com.nexo.common.mediator.Request;
import com.nexo.user.domain.entity.User;

public record RegisterUserRequest(User user) implements Request<Void> {
}
