package com.jwt.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
	private final ErrorCode errorCode;

	public CommonException(ErrorCode errorCode) {
		super(errorCode.getReason());
		this.errorCode = errorCode;
	}
}
