package com.jwt.service;

import org.springframework.stereotype.Service;

import com.jwt.domain.User;
import com.jwt.dto.request.LoginRequest;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.LoginResponse;
import com.jwt.dto.response.SignupResponse;
import com.jwt.repository.AuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthRepository authRepository;

	@Override
	public SignupResponse signup(SignupRequest signupRequest) {
		User user = User.builder()
			.username(signupRequest.getUsername())
			.password(signupRequest.getPassword())
			.nickname(signupRequest.getNickname())
			.build();

		authRepository.save(user);

		return SignupResponse.from(user);
	}

	@Override
	public LoginResponse Login(LoginRequest loginRequest) {
		return null;
	}
}
