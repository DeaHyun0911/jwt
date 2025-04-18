package com.jwt.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jwt.domain.User;

@Repository
public class AuthLocalRepository implements AuthRepository {

	private static Map<Long, User> users = new HashMap<>();
	private static long sequence = 1L;

	@Override
	public User save(User user) {
		User savedUser = User.builder()
			.id(sequence++)
			.username(user.getUsername())
			.password(user.getPassword())
			.nickname(user.getNickname())
			.role(user.getRole())
			.build();

		users.put(savedUser.getId(), savedUser);
		return savedUser;
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return users.values().stream().filter(user -> user.getUsername().equals(username)).findAny();
	}

	@Override
	public Optional<User> findById(Long id) {
		return Optional.ofNullable(users.get(id));
	}

	@Override
	public Boolean existsByUsername(String username) {
		return users.values().stream().anyMatch(user -> user.getUsername().equals(username));
	}

	@Override
	public User update(User user) {
		users.put(user.getId(), user);
		return user;
	}
}
