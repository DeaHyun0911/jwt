package com.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	USER_ALREADY_EXISTS("USER_ALREADY_EXISTS","이미 가입된 사용자입니다"),
	NOT_VALIDATION("NOT_VALIDATION", "형식이 올바르지 않습니다.");

	private final String code;
	private final String reason;
}
