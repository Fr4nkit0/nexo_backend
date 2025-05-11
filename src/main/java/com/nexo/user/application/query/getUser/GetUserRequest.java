package com.nexo.user.application.query.getUser;

import com.nexo.common.mediator.Request;

public record GetUserRequest(Long userId) implements Request<GetUserResponse> {

}
