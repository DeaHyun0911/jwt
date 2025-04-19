package com.jwt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

	@Schema(example = "daehyunchoi")
	@NotBlank(message = "이름을 입력해주세요.")
	private final String username;

	@Schema(example = "1234")
	@NotBlank(message = "비밀번호를 입력해주세요.")
	private final String password;
}
