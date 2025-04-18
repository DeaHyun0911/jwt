package com.jwt.service;

import static com.jwt.exception.ErrorCode.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.domain.User;
import com.jwt.dto.request.LoginRequest;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.LoginResponse;
import com.jwt.dto.response.user.UserResponse;
import com.jwt.exception.CommonException;
import com.jwt.repository.AuthRepository;
import com.jwt.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthRepository authRepository;
	private final BCryptPasswordEncoder PasswordEncoder;

	@Override
	public UserResponse signup(SignupRequest signupRequest) {
		if(authRepository.existsByUsername(signupRequest.getUsername())) {
			throw new CommonException(USER_ALREADY_EXISTS);
		}

		User user = User.builder()
			.username(signupRequest.getUsername())
			.password(PasswordEncoder.encode(signupRequest.getPassword()))
			.nickname(signupRequest.getNickname())
			.build();

		User savedUser = authRepository.save(user);

		return UserResponse.from(savedUser);
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		User user = authRepository.findByUsername(loginRequest.getUsername())
			.orElseThrow(() -> new CommonException(NOT_FOUND_USER));

		if(!PasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new CommonException(PASSWORD_MISMATCH);
		}

		String token = JwtUtil.createToken(user);

		return new LoginResponse(token);
	}

}
