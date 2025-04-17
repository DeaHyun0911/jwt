package com.jwt.filter;

import static com.jwt.exception.ErrorCode.*;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.config.SecurityConfig;
import com.jwt.exception.CommonException;
import com.jwt.util.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {

	private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		if(isAllowListed(requestURI)) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = extractToken(request);

		Claims claims = JwtUtil.parseToken(accessToken);

		String userName = claims.getSubject();
		String nickName = claims.get("nickName", String.class);
		String role = claims.get("role", String.class);

		System.out.println("userName = " + userName);
		System.out.println("nickName = " + nickName);
		System.out.println("role = " + role);

		Authentication authentication = new TestingAuthenticationToken("username", "password", "ROLE_TEST");
		SecurityContextHolder.getContext().setAuthentication(authentication);


		filterChain.doFilter(request, response);
	}

	private static boolean isAllowListed(String requestURI) {
		return Arrays.stream(SecurityConfig.AUTH_ALLOWLIST).anyMatch(url -> antPathMatcher.match(url, requestURI));
	}

	private String extractToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");

		if(StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		} else {
			throw new CommonException(NOT_FOUND_TOKEN);
		}
	}
}
