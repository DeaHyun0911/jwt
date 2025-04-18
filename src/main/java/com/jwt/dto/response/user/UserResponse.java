package com.jwt.dto.response.user;

import java.util.List;

import com.jwt.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

	private final Long id;
	private final String username;
	private final String nickname;
	private final List<RoleResponse> roles;

	public static UserResponse from(User user) {
		return new UserResponse(user.getId(), user.getUsername(), user.getNickname(), List.of(RoleResponse.from(user.getRole())));
	}
}
