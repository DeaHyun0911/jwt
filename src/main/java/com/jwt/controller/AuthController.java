package com.jwt.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.SignupResponse;
import com.jwt.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> signUp(@RequestBody SignupRequest signupRequest) {
		return ResponseEntity.status(CREATED).body(authService.signup(signupRequest));
	}

}
