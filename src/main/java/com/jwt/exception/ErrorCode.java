package com.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// COMMON
	NOT_VALIDATION("NOT_VALIDATION", "형식이 올바르지 않습니다."),

	// USER
	USER_ALREADY_EXISTS("USER_ALREADY_EXISTS","이미 가입된 사용자입니다"),
	PASSWORD_MISMATCH("PASSWORD_MISMATCH", "비밀번호가 일치하지 않습니다."),
	NOT_FOUND_USER("NOT_FOUND_USER", "회원정보를 찾을 수 없습니다."),

	// ADMIN
	UNAUTHORIZED("UNAUTHORIZED", "접근권한이 없습니다."),
	ACCESS_DENIED("ACCESS_DENIED", "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다"),

	// TOKEN
	NOT_FOUND_TOKEN("NOT_FOUND_TOKEN", "토큰을 찾을 수 없습니다."),
	EXPIRED_TOKEN("EXPIRED_TOKEN", "만료된 토큰입니다."),
	INVALID_TOKEN("INVALID_TOKEN", "유효하지 않은 토큰입니다");



	private final String code;
	private final String reason;
}
