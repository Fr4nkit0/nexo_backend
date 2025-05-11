package com.nexo.user.application.query.getUser;

import com.nexo.common.mediator.RequestHandler;
import com.nexo.user.domain.entity.User;
import com.nexo.user.domain.exception.UserNotFoundException;
import com.nexo.user.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserHandler implements RequestHandler<GetUserRequest, GetUserResponse> {

	private final UserRepository userRepository;

	@Override
	public GetUserResponse handle(GetUserRequest request) {

		User user = userRepository.findById(request.userId()).orElseThrow(() -> new UserNotFoundException("User not found"));

		return new GetUserResponse(user);
	}

	@Override
	public Class<GetUserRequest> getRequestType() {
		return GetUserRequest.class;
	}
}