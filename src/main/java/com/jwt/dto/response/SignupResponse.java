package com.jwt.dto.response;

import com.jwt.domain.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponse {
	private final String username;
	private final String nickname;
	private final UserRole userRole;
}
