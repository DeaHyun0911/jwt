package com.jwt.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.dto.request.LoginRequest;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.LoginResponse;
import com.jwt.dto.response.user.UserResponse;
import com.jwt.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name="auth", description = "회원관리 API")
public class AuthController {

	private final AuthService authService;

	@Operation(description = "회원가입")
	@PostMapping("/signup")
	public ResponseEntity<UserResponse> signUp(@RequestBody @Valid SignupRequest signupRequest) {
		return ResponseEntity.status(CREATED).body(authService.signup(signupRequest));
	}

	@Operation(description = "로그인")
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
		LoginResponse response = authService.login(loginRequest);

		return ResponseEntity.status(OK)
			.header("Authorization", "Bearer " + response.getToken())
			.body(response);
	}

}
