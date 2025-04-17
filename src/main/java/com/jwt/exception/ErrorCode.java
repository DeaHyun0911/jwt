package com.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	USER_ALREADY_EXISTS("USER_ALREADY_EXISTS","이미 가입된 사용자입니다"),
	PASSWORD_MISMATCH("PASSWORD_MISMATCH", "비밀번호가 일치하지 않습니다."),
	NOT_FOUND_USER("NOT_FOUND_USER", "회원정보를 찾을 수 없습니다."),
	NOT_FOUND_TOKEN("NOT_FOUND_TOKEN", "토큰을 찾을 수 없습니다."),
	NOT_VALIDATION("NOT_VALIDATION", "형식이 올바르지 않습니다.");

	private final String code;
	private final String reason;
}
