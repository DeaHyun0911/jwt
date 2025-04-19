package com.jwt.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.domain.UserRole;
import com.jwt.dto.request.LoginRequest;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.LoginResponse;
import com.jwt.dto.response.user.RoleResponse;
import com.jwt.dto.response.user.UserResponse;
import com.jwt.service.AuthService;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

	private static final String AUTH_URL = "/auth";

	@MockitoBean
	AuthService authService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@BeforeEach
	void 로그인유저() {

	}

	@Test
	void 회원가입_성공() throws Exception {
		//given
		SignupRequest request = new SignupRequest("test1", "1234", "nickname1");
		UserResponse response = new UserResponse(1L,"test1", "nickname1", List.of(RoleResponse.from(UserRole.USER)));
		when(authService.signup(any(SignupRequest.class))).thenReturn(response);

		//when
		String content = this.mockMvc.perform(
			post(AUTH_URL + "/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andReturn()
			.getResponse()
			.getContentAsString();

		UserResponse actualResult = this.mapper.readValue(content, UserResponse.class);

		//then
		assertThat(actualResult)
			.usingRecursiveComparison()
			.isEqualTo(response);

	}

	@Test
	void 회원가입_로그인_유저이름_미입력시_예외발생() throws Exception {
		//given
		SignupRequest request = new SignupRequest("", "1234", "nickname1");
		//when
		mockMvc.perform(
				post(AUTH_URL + "/signup")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error.code").value("NOT_VALIDATION"))
			.andExpect(jsonPath("$.error.message").value("이름을 입력해주세요."));

		verify(authService, times(0)).signup(any(SignupRequest.class));
	}

	@Test
	void 회원가입_로그인_비밀번호_미입력시_예외발생() throws Exception {
		//given
		SignupRequest request = new SignupRequest("test2", "", "nickname1");
		//when
		mockMvc.perform(
				post(AUTH_URL + "/signup")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error.code").value("NOT_VALIDATION"))
			.andExpect(jsonPath("$.error.message").value("비밀번호를 입력해주세요."));

		verify(authService, times(0)).signup(any(SignupRequest.class));
	}

	@Test
	void 회원가입_닉네임_미입력시_예외발생() throws Exception {
		//given
		SignupRequest request = new SignupRequest("test2", "1234", " ");
		//when
		mockMvc.perform(
				post(AUTH_URL + "/signup")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error.code").value("NOT_VALIDATION"))
			.andExpect(jsonPath("$.error.message").value("닉네임을 입력해주세요."));

		verify(authService, times(0)).signup(any(SignupRequest.class));
	}

	@Test
	void 로그인_성공() throws Exception {
		// given
		LoginRequest request = new LoginRequest("test", "1234");
		LoginResponse response = new LoginResponse("token");

		when(authService.login(any(LoginRequest.class))).thenReturn(response);

		//when
		String content = this.mockMvc.perform(
				post(AUTH_URL + "/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(header().string("Authorization", "Bearer token"))
			.andReturn()
			.getResponse()
			.getContentAsString();

		LoginResponse actualResult = this.mapper.readValue(content, LoginResponse.class);

		//then
		assertThat(actualResult)
			.usingRecursiveComparison()
			.isEqualTo(response);

		verify(authService, times(1)).login(any(LoginRequest.class));
	}
}