package com.jwt.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
	private final String username;
	private final String password;
	private final String nickname;
	private final UserRole role = UserRole.USER;

	@Builder
	public User(String username, String password, String nickname) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}
}
