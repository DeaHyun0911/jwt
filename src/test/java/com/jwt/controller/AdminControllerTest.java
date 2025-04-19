package com.jwt.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.dto.request.LoginRequest;
import com.jwt.dto.request.SignupRequest;
import com.jwt.repository.AuthRepository;
import com.jwt.service.AdminService;
import com.jwt.service.AuthService;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
class AdminControllerTest {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private AuthService authService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String URL = "/admin/users/1/roles";
	private static final String WRONG_URL = "/admin/users/10/roles";

	@BeforeEach
	void setUp() {
		SignupRequest request = new SignupRequest("nomal", "1234", "일반유저");
		authService.signup(request);

		SignupRequest adminRequest1 = new SignupRequest("admin", "1234", "관리자");
		adminService.create(adminRequest1);
	}

	@AfterEach
	void clear() {
		authRepository.clear();
	}

	@Test
	void 관리자권한_있을때_성공() throws Exception {
		//given
		String adminToken = authService.login(new LoginRequest("admin", "1234")).getToken();

		//then
		mockMvc.perform(patch(URL)
							.header("Authorization", "Bearer " + adminToken))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();

	}

	@Test
	void 관리자권한_없을때_실패() throws Exception {
		//given
		String userToken = authService.login(new LoginRequest("nomal", "1234")).getToken();

		//then
		mockMvc.perform(patch(URL)
							.header("Authorization", "Bearer " + userToken))
			.andExpect(status().isForbidden());
	}

	@Test
	void 없는사용자_요청할때_실패() throws Exception {
		//given
		String adminToken = authService.login(new LoginRequest("admin", "1234")).getToken();

		//then
		mockMvc.perform(patch(WRONG_URL)
							.header("Authorization", "Bearer " + adminToken))
			.andExpect(status().isBadRequest());

	}

}