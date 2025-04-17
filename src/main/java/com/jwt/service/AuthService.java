package com.jwt.service;

import com.jwt.dto.request.LoginRequest;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.LoginResponse;
import com.jwt.dto.response.SignupResponse;

public interface AuthService {

	SignupResponse signup(SignupRequest signupRequest);

	LoginResponse Login(LoginRequest loginRequest);
}
