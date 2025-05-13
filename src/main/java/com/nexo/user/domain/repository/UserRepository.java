package com.nexo.user.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nexo.user.domain.persistence.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.email=?1")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id !=?1")
    List<User> findAllUsersExceptSelf(UUID publicId);

    @Query("SELECT u FROM User u WHERE u.id =?1")
    Optional<User> findByPublicId(UUID publicId);

    @Query("SELECT u FROM User u WHERE u.username =?1")
    Optional<User> findByUsername(String username);
}
