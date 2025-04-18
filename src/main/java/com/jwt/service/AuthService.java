package com.jwt.service;

import com.jwt.dto.request.LoginRequest;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.LoginResponse;
import com.jwt.dto.response.user.UserResponse;

public interface AuthService {

	UserResponse signup(SignupRequest signupRequest);

	LoginResponse login(LoginRequest loginRequest);

}
