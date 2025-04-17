package com.jwt.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequest {

	@NotBlank(message = "이름을 입력해주세요.")
	private final String username;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	private final String password;

	@NotBlank(message = "닉네임을 입력해주세요.")
	private final String nickname;
}
