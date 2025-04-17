package com.jwt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequest {
	private final String username;
	private final String password;
	private final String nickname;
}
