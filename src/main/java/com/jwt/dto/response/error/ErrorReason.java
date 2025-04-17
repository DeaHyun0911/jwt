package com.jwt.dto.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorReason {
	private final String code;
	private final String message;

	public static ErrorReason of(final String code, final String message) {
		return new ErrorReason(code, message);
	}
}
