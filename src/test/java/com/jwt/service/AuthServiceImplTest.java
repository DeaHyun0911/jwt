package com.jwt.service;

import static com.jwt.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jwt.domain.User;
import com.jwt.domain.UserRole;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.RoleResponse;
import com.jwt.dto.response.user.SignupResponse;
import com.jwt.exception.CommonException;
import com.jwt.exception.ErrorCode;
import com.jwt.repository.AuthRepository;

@ExtendWith(SpringExtension.class)
class AuthServiceImplTest {

	@InjectMocks
	private AuthServiceImpl authService;

	@Mock
	private AuthRepository authRepository;

	@Test
	void signup_success() {
		//given
		SignupRequest signupRequest = new SignupRequest("test", "1234", "nickname");
		when(authRepository.existsByUsername(anyString())).thenReturn(false);
		User user = User.builder()
			.username(signupRequest.getUsername())
			.password(signupRequest.getPassword())
			.nickname(signupRequest.getNickname())
			.build();

		//when
		SignupResponse actualResult = authService.signup(signupRequest);

		//then
		SignupResponse expectedResult = new SignupResponse("test", "nickname", List.of(RoleResponse.from(UserRole.USER)));
		assertThat(actualResult)
			.usingRecursiveComparison()
			.isEqualTo(expectedResult);
		verify(authRepository, times(1)).save(any(User.class));

	}

	@Test
	void signup_fail() {
		//given
		SignupRequest signupRequest = new SignupRequest("test", "1234", "nickname");
		when(authRepository.existsByUsername(anyString())).thenReturn(true);

		//when & then
		assertThatThrownBy(() -> {
			authService.signup(signupRequest);
		}).isInstanceOf(CommonException.class)
			.hasMessageContaining(USER_ALREADY_EXISTS.getReason());
	}
}