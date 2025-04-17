package com.jwt.repository;

import java.util.Optional;

import com.jwt.domain.User;

public interface AuthRepository {
	User save(User user);
	Optional<User> findByUsername(String username);
}
