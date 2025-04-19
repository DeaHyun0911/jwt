package com.jwt.service;

import static com.jwt.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jwt.domain.User;
import com.jwt.domain.UserRole;
import com.jwt.dto.request.LoginRequest;
import com.jwt.dto.request.SignupRequest;
import com.jwt.dto.response.user.LoginResponse;
import com.jwt.dto.response.user.RoleResponse;
import com.jwt.dto.response.user.UserResponse;
import com.jwt.exception.CommonException;
import com.jwt.repository.AuthRepository;

@ExtendWith(SpringExtension.class)
class AuthServiceImplTest {

	@InjectMocks
	private AuthServiceImpl authService;

	@Mock
	private AuthRepository authRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	void 회원가입_성공() {
		//given
		SignupRequest signupRequest = new SignupRequest("test", "1234", "nickname");
		when(authRepository.existsByUsername(anyString())).thenReturn(false);
		User user = User.builder()
			.id(1L)
			.username(signupRequest.getUsername())
			.password(signupRequest.getPassword())
			.nickname(signupRequest.getNickname())
			.build();
		when(authRepository.save(any(User.class))).thenReturn(user);

		//when
		UserResponse actualResult = authService.signup(signupRequest);

		//then
		UserResponse expectedResult = new UserResponse(1L,"test", "nickname", List.of(RoleResponse.from(UserRole.USER)));
		assertThat(actualResult)
			.usingRecursiveComparison()
			.isEqualTo(expectedResult);
		verify(authRepository, times(1)).save(any(User.class));

	}

	@Test
	void 이미_가입자일경우_예외발생() {
		//given
		SignupRequest signupRequest = new SignupRequest("test", "1234", "nickname");
		when(authRepository.existsByUsername(anyString())).thenReturn(true);

		//when & then
		assertThatThrownBy(() -> {
			authService.signup(signupRequest);
		}).isInstanceOf(CommonException.class)
			.hasMessageContaining(USER_ALREADY_EXISTS.getReason());
	}

	@Test
	void 로그인_성공() {
		// given
		LoginRequest request = new LoginRequest("test", "1234");

		User loginUser = User.builder()
			.id(1L)
			.username(request.getUsername())
			.password(passwordEncoder.encode(request.getPassword()))
			.build();

		when(authRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(loginUser));
		when(passwordEncoder.matches(request.getPassword(), loginUser.getPassword())).thenReturn(true);

		// when
		LoginResponse result = authService.login(request);

		assertThat(result.getToken()).isNotBlank();
		verify(authRepository, times(1)).findByUsername(request.getUsername());

	}

	@Test
	void 로그인_없는유저_실패() {
		// given
		LoginRequest request = new LoginRequest("test", "1234");
		when(authRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

		// then
		assertThatThrownBy(() -> authService.login(request))
			.isInstanceOf(CommonException.class)
			.hasMessageContaining(NOT_FOUND_USER.getReason());
	}

	@Test
	void 로그인_비밀번호_틀림() {
		// given
		LoginRequest request = new LoginRequest("test", "1234");

		User loginUser = User.builder()
			.id(1L)
			.username(request.getUsername())
			.password(passwordEncoder.encode("12345"))
			.build();

		when(authRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(loginUser));
		when(passwordEncoder.matches(request.getPassword(), loginUser.getPassword())).thenReturn(false);

		// then
		assertThatThrownBy(() -> authService.login(request))
			.isInstanceOf(CommonException.class)
			.hasMessageContaining(PASSWORD_MISMATCH.getReason());

	}
}