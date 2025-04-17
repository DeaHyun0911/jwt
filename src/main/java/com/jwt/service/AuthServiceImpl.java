package com.jwt.service;

import static com.jwt.exception.ErrorCode.*;

import org.springframework.stereotype.Service;

import com.jwt.domain.User;
import com.jwt.dto.request.LoginRequest;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.LoginResponse;
import com.jwt.dto.response.user.SignupResponse;
import com.jwt.exception.CommonException;
import com.jwt.repository.AuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthRepository authRepository;

	@Override
	public SignupResponse signup(SignupRequest signupRequest) {
		if(authRepository.existsByUsername(signupRequest.getUsername())) {
			throw new CommonException(USER_ALREADY_EXISTS);
		}

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
