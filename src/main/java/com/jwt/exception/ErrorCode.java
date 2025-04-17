package com.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	USER_ALREADY_EXISTS("USER_ALREADY_EXISTS","이미 가입된 사용자입니다");

	private final String code;
	private final String reason;
}
