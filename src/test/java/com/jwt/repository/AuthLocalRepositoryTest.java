package com.jwt.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.jwt.domain.User;

class AuthLocalRepositoryTest {

	AuthLocalRepository authLocalRepository = new AuthLocalRepository();

	@Test
	public void save() {
		//given
		User user = User.builder()
			.username("test")
			.password("1234")
			.nickname("nickname")
			.build();

		//when
		authLocalRepository.save(user);
		User result = authLocalRepository.findByUsername(user.getUsername()).get();

		//then
		assertThat(user).isEqualTo(result);
	}

	@Test
	public void findByUsername() {
		//given
		User user = User.builder()
			.username("test1")
			.password("1234")
			.nickname("nickname")
			.build();
		authLocalRepository.save(user);

		//when
		User result = authLocalRepository.findByUsername(user.getUsername()).get();

		//then
		assertThat(user).isEqualTo(result);
	}

	@Test
	public void existsByUsername() {
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
	public void notExistsByUsername() {
		//when
		boolean result = authLocalRepository.existsByUsername("test3");

		//then
		assertThat(result).isFalse();
	}

}