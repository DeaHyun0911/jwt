package com.jwt.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jwt.domain.User;
import com.jwt.domain.UserRole;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.UserResponse;
import com.jwt.repository.AuthRepository;

@ExtendWith(SpringExtension.class)
class AdminServiceImplTest {

	@InjectMocks
	private AdminServiceImpl adminService;

	@Mock
	private AuthRepository authRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	void 관리자권한_부여_성공() {
		//given
		User user = User.builder().id(1L).username("test").password("1234").build();

		//when
		when(authRepository.findById(1L)).thenReturn(Optional.of(user));
		when(authRepository.update(user)).thenReturn(user);

		UserResponse response = adminService.grantAdminRole(1L);

		//then
		Assertions.assertThat(UserRole.ADMIN.toString()).isEqualTo(response.getRoles().get(0).getRole());

	}

	@Test
	void 관리자계정_생성() {
		//given
		SignupRequest signupRequest = new SignupRequest("test", "1234", "nickname");
		when(authRepository.existsByUsername(anyString())).thenReturn(false);
		when(authRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

		//when
		UserResponse actualResult = adminService.create(signupRequest);

		//then
		assertThat(actualResult.getUsername()).isEqualTo("test");
		assertThat(actualResult.getNickname()).isEqualTo("nickname");
		assertThat(actualResult.getRoles().get(0).getRole()).isEqualTo(UserRole.ADMIN.toString());
	}
}