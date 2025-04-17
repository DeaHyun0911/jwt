package com.jwt.dto.response.error;

import static com.jwt.exception.ErrorCode.*;

import com.jwt.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	private final ErrorReason error;

	public static ErrorResponse from(ErrorCode error) {
		return new ErrorResponse(ErrorReason.of(error.getCode(), error.getReason()));
	}

	public static ErrorResponse from(String message) {
		return new ErrorResponse(ErrorReason.of(NOT_VALIDATION.getCode(), message));
	}
}
