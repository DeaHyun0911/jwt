package com.jwt.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.jwt.domain.User;
import com.jwt.domain.UserRole;

class AuthLocalRepositoryTest {

	AuthLocalRepository authLocalRepository = new AuthLocalRepository();

	@AfterEach
	void dataInit() {
		authLocalRepository.clear();
	}

	@Test
	public void 유저저장_성공() {
		//given
		User user = User.builder()
			.id(1L)
			.username("test")
			.password("1234")
			.nickname("nickname")
			.build();

		//when
		authLocalRepository.save(user);
		User result = authLocalRepository.findByUsername(user.getUsername()).get();

		//then
		assertThat(user)
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(result);
	}

	@Test
	public void 이름으로_유저찾기_성공() {
		//given
		User user = User.builder()
			.id(1L)
			.username("test1")
			.password("1234")
			.nickname("nickname")
			.build();
		authLocalRepository.save(user);

		//when
		User result = authLocalRepository.findByUsername(user.getUsername()).get();

		//then
		assertThat(user)
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(result);
	}

	@Test
	public void 유저이름이_존재하면_true() {
		//given
		User user = User.builder()
			.username("test2")
			.password("1234")
			.nickname("nickname")
			.build();
		authLocalRepository.save(user);

		//when
		boolean result = authLocalRepository.existsByUsername(user.getUsername());

		//then
		assertThat(result).isTrue();
	}

	@Test
	public void 유저이름이_존재하지않으면_False() {
		//when
		boolean result = authLocalRepository.existsByUsername("test3");

		//then
		assertThat(result).isFalse();
	}

	@Test
	public void 유저정보_업데이트() {
		//given
		User user = User.builder()
			.id(1L)
			.username("test")
			.password("1234")
			.nickname("nickname")
			.build();
		User savedUser = authLocalRepository.save(user);

		//when
		User Updateduser = User.builder()
			.id(1L)
			.username("test")
			.password("1234")
			.nickname("nickname")
			.role(UserRole.ADMIN)
			.build();
		User result = authLocalRepository.update(Updateduser);

		//then
		assertThat(UserRole.ADMIN).isEqualTo(result.getRole());
	}

}