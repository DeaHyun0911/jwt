package com.jwt.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.UserResponse;
import com.jwt.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "관리자 계정 생성", description = "테스트용 관리자 계정을 생성할 수 있습니다.")
public class CreateAdminController {

	private final AdminService adminService;

	@Operation(description = "관리자 계정 생성")
	@PostMapping("/create-admin")
	public ResponseEntity<UserResponse> createAdmin(@RequestBody @Valid SignupRequest signupRequest) {
		return ResponseEntity.status(CREATED).body(adminService.create(signupRequest));
	}
}
