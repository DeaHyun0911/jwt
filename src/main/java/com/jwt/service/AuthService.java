package com.jwt.service;

import com.jwt.dto.request.LoginRequest;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.LoginResponse;
import com.jwt.dto.response.user.SignupResponse;

public interface AuthService {

	SignupResponse signup(SignupRequest signupRequest);

	LoginResponse login(LoginRequest loginRequest);
}
