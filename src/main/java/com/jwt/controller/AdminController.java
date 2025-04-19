package com.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.dto.response.user.UserResponse;
import com.jwt.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "관리자 전용 API")
public class AdminController {

	private final AdminService adminService;

	@Operation(description = "관리자 권한 부여")
	@PatchMapping("/users/{userId}/roles")
	public ResponseEntity<UserResponse> grantAdminRole(@PathVariable Long userId) {
		System.out.println("userId = " + userId);
		return ResponseEntity.status(HttpStatus.OK).body(adminService.grantAdminRole(userId));
	}
}
