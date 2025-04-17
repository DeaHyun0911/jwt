package com.jwt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
	private final Long id;
	private final String username;
	private final String password;
	private final String nickname;
	private final UserRole role = UserRole.USER;
}
