package com.jwt.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.domain.UserRole;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.RoleResponse;
import com.jwt.dto.response.user.SignupResponse;
import com.jwt.service.AuthServiceImpl;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

	private static final String AUTH_URL = "/auth";

	@MockitoBean
	AuthServiceImpl authService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void signup_success() throws Exception {
		//given
		SignupRequest request = new SignupRequest("test1", "1234", "nickname1");
		SignupResponse response = new SignupResponse("test1", "nickname1", List.of(RoleResponse.from(UserRole.USER)));
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

		SignupResponse actualResult = this.mapper.readValue(content, SignupResponse.class);

		//then
		assertThat(actualResult)
			.usingRecursiveComparison()
			.isEqualTo(response);

	}

	@Test
	void empty_username() throws Exception {
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
	void empty_password() throws Exception {
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
	void empty_nickname() throws Exception {
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
}