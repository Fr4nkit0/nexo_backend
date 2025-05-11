package com.nexo.user.application.query.getAllUsers;


import com.nexo.common.mediator.RequestHandler;
import com.nexo.user.domain.entity.User;
import com.nexo.user.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllUsersHandler implements RequestHandler<GetAllUsersRequest, GetAllUsersResponse> {

	private final UserRepository userRepository;

	@Override
	public GetAllUsersResponse handle(GetAllUsersRequest request) {

		List<User> users = userRepository.findAll();

		return new GetAllUsersResponse(users);
	}

	@Override
	public Class<GetAllUsersRequest> getRequestType() {
		return GetAllUsersRequest.class;
	}
}