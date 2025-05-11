package com.nexo.user.application.command.deleteUser;

import com.nexo.common.mediator.RequestHandler;
import com.nexo.user.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public  class DeleteUserHandler implements RequestHandler<DeleteUserRequest, Void> {
	private final UserRepository userRepository;

	@Override
	public Void handle(DeleteUserRequest request) {
		if (request.userId() == null) {
			throw new IllegalArgumentException("User ID cannot be null");
		}
		userRepository.deleteById(request.userId());
		return null;
	}

	@Override
	public Class<DeleteUserRequest> getRequestType() {
		return DeleteUserRequest.class;
	}
}
