package com.nexo.user.domain.port;

import com.nexo.user.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
	User save(User userEntity);

	Optional<User> findById(Long id);

	List<User> findAll();

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

	void deleteById(Long id);

	void updateById (Long id, User updateUser);


}
