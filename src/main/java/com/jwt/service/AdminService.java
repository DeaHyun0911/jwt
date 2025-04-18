package com.jwt.service;

import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.UserResponse;

import jakarta.validation.Valid;

public interface AdminService {
	UserResponse grantAdminRole(Long userId);

	UserResponse create(@Valid SignupRequest signupRequest);
}
