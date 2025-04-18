package com.jwt.service;

import static com.jwt.exception.ErrorCode.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.domain.User;
import com.jwt.domain.UserRole;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.UserResponse;
import com.jwt.exception.CommonException;
import com.jwt.repository.AuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AuthRepository authRepository;
	private final BCryptPasswordEncoder PasswordEncoder;

	@Override
	public UserResponse grantAdminRole(Long userId) {
		User user = authRepository.findById(userId).orElseThrow(() -> new CommonException(NOT_FOUND_USER));

		user.updateRole(UserRole.ADMIN);
		User savedUser = authRepository.update(user);

		return UserResponse.from(savedUser);
	}

	@Override
	public UserResponse create(SignupRequest signupRequest) {
		if(authRepository.existsByUsername(signupRequest.getUsername())) {
			throw new CommonException(USER_ALREADY_EXISTS);
		}

		User user = User.builder()
			.username(signupRequest.getUsername())
			.password(PasswordEncoder.encode(signupRequest.getPassword()))
			.nickname(signupRequest.getNickname())
			.role(UserRole.ADMIN)
			.build();

		User savedUser = authRepository.save(user);

		return UserResponse.from(savedUser);
	}
}
