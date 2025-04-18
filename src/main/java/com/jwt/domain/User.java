package com.jwt.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
	private final Long id;
	private final String username;
	private final String password;
	private final String nickname;

	@Builder.Default
	private UserRole role = UserRole.USER;

	public void updateRole(UserRole role) {
		this.role = role;
	}
}
