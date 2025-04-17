package com.jwt.util;

import static com.jwt.util.JwtUtil.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.jwt.domain.User;
import com.jwt.domain.UserRole;

public class JwtUtilTest {

	@Test
	public void createToken() {
		String userName = "test";
		String nickName = "nickname";

		User user = User.builder()
			.username(userName)
			.password("123456")
			.nickname(nickName)
			.build();

		String token = JwtUtil.createToken(user);

		assertThat(userName).isEqualTo(parseToken(token).getSubject());
		assertThat(nickName).isEqualTo(parseToken(token).get("nickname"));
		assertThat(UserRole.USER.toString()).isEqualTo(parseToken(token).get("roles"));
	}

}
