package com.jwt.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import com.jwt.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private static final long EXPIRATION_TIME = 1000 * 60 * 60;

	public static String createToken(User user) {

		Date now = new Date();
		Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

		Map<String, Object> claims = new HashMap<>();
		claims.put("nickname", user.getNickname());
		claims.put("roles", user.getRole());

		return Jwts.builder()
			.subject(user.getUsername())
			.claims(claims)
			.issuedAt(now)
			.expiration(expiration)
			.signWith(SECRET_KEY)
			.compact();
	}

	public static Claims parseToken(String token) {
		return Jwts.parser()
			.verifyWith(SECRET_KEY)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
