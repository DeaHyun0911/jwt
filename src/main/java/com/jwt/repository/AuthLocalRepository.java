package com.jwt.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jwt.domain.User;

@Repository
public class AuthLocalRepository implements AuthRepository {

	private static Map<String, User> users = new HashMap<>();

	@Override
	public User save(User user) {
		users.put(user.getUsername(),user);
		return user;
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return Optional.ofNullable(users.get(username));
	}

	@Override
	public Boolean existsByUsername(String username) {
		return users.containsKey(username);
	}
}
