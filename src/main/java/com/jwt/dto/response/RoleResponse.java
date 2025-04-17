package com.jwt.dto.response;

import com.jwt.domain.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleResponse {

	private final String role;

	public static RoleResponse from(UserRole role) {
		return new RoleResponse(role.name());
	}
}
