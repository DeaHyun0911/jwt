package com.jwt.dto.response;

import java.util.List;

import com.jwt.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponse {
	private final String username;
	private final String nickname;
	private final List<RoleResponse> userRole;

	public static SignupResponse from(User user) {
		return new SignupResponse(user.getUsername(), user.getNickname(), List.of(RoleResponse.from(user.getRole())));
	}
}
