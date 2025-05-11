package com.nexo.user.application.command.deleteUser;

import com.nexo.common.mediator.Request;

public record DeleteUserRequest(Long userId) implements Request<Void> {
}
